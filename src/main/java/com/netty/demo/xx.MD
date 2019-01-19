# 使用Kubernetes和Spring Boot进行自我修复的应用程序
[原文链接](https://www.baeldung.com/spring-boot-kubernetes-self-healing-apps) https://www.baeldung.com/spring-boot-kubernetes-self-healing-apps

作者: Daniel Barrigas 

译者: skeeve 

### 1 简介
在本教程中，我们将讨论  Kubernetes的探测器，  并演示如何利用  Actuator的  HealthIndicator  准确了解我们的应用程序状态。

出于本教程的目的，我们将假设一些预先存在的  Spring Boot Actuator，  Kubernetes和  Docker的经验。

### 2  Kubernetes 探针
Kubernetes定义了两种不同的探针，我们可以用它来定期检查一切是否按预期工作：  活跃度探针和准备度探针。

#### 2.1 活跃度探针和准备度探针
通过Liveness和  Readiness探测器，  Kubelet  可以在检测到某些东西时立即行动，并最大限度地减少应用程序的停机时间。

两者都以相同的方式配置，但它们具有不同的语义，Kubelet  根据触发的操作执行不同的操作：

 * 准备就绪 -  准备情况验证我们的  Pod是否已准备好开始接收流量。当所有容器准备就绪时，我们的  P od已经准备就绪
 * 活力 - 与准备情况相反  ，  活跃度检查我们的  Pod应该重新启动。它可以获取我们的应用程序正在运行但处于无法取得进展的状态的用例; 例如，它处于死锁状态
 
我们在容器级别配置两种探测器类型：

```markdown
apiVersion: v1
kind: Pod
metadata:
  name: goproxy
  labels:
    app: goproxy
spec:
  containers:
  - name: goproxy
    image: k8s.gcr.io/goproxy:0.1
    ports:
    - containerPort: 8080
    readinessProbe:
      tcpSocket:
        port: 8080
      initialDelaySeconds: 5
      periodSeconds: 10
      timeoutSeconds: 2
      failureThreshold: 1
      successThreshold: 1
    livenessProbe:
      tcpSocket:
        port: 8080
      initialDelaySeconds: 15
      periodSeconds: 20
      timeoutSeconds: 2
      failureThreshold: 1
      successThreshold: 1
```

我们可以配置许多字段来更精确地控制探针的行为：

* initialDelaySeconds - 创建容器后，在启动探测之前等待n 秒.
* periodSeconds - 应该运行此探测的频率，默认为10秒; 最小值是1秒.
* timeoutSeconds - 在超时探测之前等待多长时间，默认为1秒; 最小值再次为1秒.
* failureThreshold -  在放弃之前尝试  n次。在准备就绪的情况下  ，我们的pod将被标记为未准备好，而在活跃的情况下放弃意味着重新启动  Pod。此处的默认值为3次失败，最小值为1.
* successThreshold - 这是失败后探测成功的最小连续成功次数。默认为1成功，最小值为1.

在这种情况下，我们选择了  tcp探测器，但是，我们也可以使用其他类型的探测器。

#### 2.2 探针类型
根据我们的使用情况，一种探针类型可能比另一种更有用。例如，如果我们的容器是Web服务器，则使用  http探测器可能比tcp探测器更可靠  。

幸运的是，  Kubernetes有三种不同类型的探针可供我们使用：

* exec - 在我们的容器中执行  bash指令。例如，检查特定文件是否存在。如果指令返回失败代码，则探测失败
* tcpSocket - 尝试 使用指定的端口建立与容器的tcp连接。如果无法建立连接，则探测失败
* httpGet - 将HTTP GET请求发送到在容器中运行并侦听指定端口的服务器。任何大于或等于200且小于400的代码表示成功

重要的是要注意  HTTP探测器除了前面提到的那些之外还有其他字段：

* host - 要连接的主机名，默认为我们的pod的IP
* scheme - 应该用于连接， HTTP或 HTTPS的方案，默认为  HTTP
* path - Web服务器上的访问路径
* httpHeaders - 要在请求中设置的自定义标头
* port - 要在容器中访问的端口的名称或编号
### 3 Spring actuator 和 Kubernetes 的自愈能力
现在，现在我们大致了解了kubernetes如何能够检测我们的应用程序是否崩溃，让我们看看，怎么利用的 Spring的 actuator，以保持更密切的关注我们的应用程序，以及它的依赖！

出于这些示例的目的，我们将依赖于  [Minikube](https://www.baeldung.com/spring-boot-minikube)。

#### 3.1 actuator和它的 HealthIndicators
考虑到Spring有许多  HealthIndicator可供使用，反映我们的一些应用程序依赖状态，通过Kubernetes的探测器；就像在我们的pom.xml中添加Actuator依赖一样简单  ：

```markdown
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### 3.2 真实示例
让我们从一个应用程序开始，该应用程序将正常启动，30s后将转换到一个损坏状态。

我们将通过创建一个 验证布尔变量是否为真的[HealthIndicator](https://www.baeldung.com/spring-boot-actuators)来模拟一个异常的状态。我们将变量初始化为  true，然后我们将安排任务在30秒后将其更改为  false：
```markdown
@Component
public class CustomHealthIndicator implements HealthIndicator {
 
    private boolean isHealthy = true;
 
    public CustomHealthIndicator() {
        ScheduledExecutorService scheduled =
          Executors.newSingleThreadScheduledExecutor();
        scheduled.schedule(() -> {
            isHealthy = false;
        }, 30, TimeUnit.SECONDS);
    }
 
    @Override
    public Health health() {
        return isHealthy ? Health.up().build() : Health.down().build();
    }
}
```

有了我们的  HealthIndicator，我们需要将我们的应用程序容器化：

```markdown
FROM openjdk:8-jdk-alpine
RUN mkdir -p /usr/opt/service
COPY target/*.jar /usr/opt/service/service.jar
EXPOSE 8080
ENTRYPOINT exec java -jar /usr/opt/service/service.jar

```
接下来，我们创建我们的  Kubernetes模板：

```markdown

apiVersion: apps/v1
kind: Deployment
metadata:
  name: liveness-example
spec:
  ...
    spec:
      containers:
      - name: liveness-example
        image: dbdock/liveness-example:1.0.0
        ...
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 10
          timeoutSeconds: 2
          periodSeconds: 3
          failureThreshold: 1
        livenessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 20
          timeoutSeconds: 2
          periodSeconds: 8
          failureThreshold: 1
```

**我们正在使用指向Actuator健康端点的httpGet探针。** 对我们的应用程序状态（及其依赖项）的任何更改都将反映在我们部署的健康状况上。

在将我们的应用程序部署到  Kubernetes后，我们将能够看到两个探测器在运行：大约30秒后，我们的  Pod将被标记为未准备好并从旋转中移除; 几秒钟后，  Pod重新启动。

我们可以看到我们的Pod  执行  kubectl 的事件描述了 pod liveness -example：

```markdown

Warning  Unhealthy 3s (x2 over 7s)   kubelet, minikube  Readiness probe failed: HTTP probe failed ...
Warning  Unhealthy 1s                kubelet, minikube  Liveness probe failed: HTTP probe failed ...
Normal   Killing   0s                kubelet, minikube  Killing container with id ...
```
### 3.3 准备范例
在前面的示例中，我们了解了如何使用  HealthIndicator来反映应用程序在Kubernetes部署的健康状况方面的状态  。

让我们用它在不同的使用情况：假设我们的应用程序需要 一个 位 的 时间 之前， 它 能够 对 接收 的流量。例如，它需要将文件加载到内存中并验证其内容。

这是我们何时可以利用准备情况探测的一个很好的例子  。

让我们修改 上一个示例中的  HealthIndicator和  Kubernetes模板，并使它们适应这个用例：
```markdown
@Component
public class CustomHealthIndicator implements HealthIndicator {
 
    private boolean isHealthy = false;
 
    public CustomHealthIndicator() {
        ScheduledExecutorService scheduled =
          Executors.newSingleThreadScheduledExecutor();
        scheduled.schedule(() -> {
            isHealthy = true;
        }, 40, TimeUnit.SECONDS);
    }
 
    @Override
    public Health health() {
        return isHealthy ? Health.up().build() : Health.down().build();
    }
}
```

我们将变量初始化为false，并在40秒后执行任务并将其设置为  true。

接下来，我们使用以下模板对我们的应用程序进行容器化并部署：
```markdown
apiVersion: apps/v1
kind: Deployment
metadata:
  name: readiness-example
spec:
  ...
    spec:
      containers:
      - name: readiness-example
        image: dbdock/readiness-example:1.0.0
        ...
        readinessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 40
          timeoutSeconds: 2
          periodSeconds: 3
          failureThreshold: 2
        livenessProbe:
          httpGet:
            path: /health
            port: 8080
          initialDelaySeconds: 100
          timeoutSeconds: 2
          periodSeconds: 8
          failureThreshold: 1
```

虽然类似，但我们需要指出的探针配置有一些变化：

* 因为我们知道，我们的应用程序需要40秒左右，才能准备好来接收的流量，我们增加了  initialDelaySeconds  我们的准备探测到40秒
* 同样，我们增加了  initialDelaySeconds 活跃度初始化探针时间到100秒，以避免过早地被Kubernetes杀死  
如果在40秒后仍然没有完成，它仍然有大约60秒来完成。之后，我们的  活跃度探针将启动并重新启动  Pod。

### 4 结论
在本文中，我们讨论了Kubernetes探针以及如何使用Spring的  Actuator 来改进我们的应用程序的健康监控。

可以在[Github](https://github.com/eugenp/tutorials/tree/master/spring-cloud/spring-cloud-kubernetes)上找到这些示例的完整实现。


        