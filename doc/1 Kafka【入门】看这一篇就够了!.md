## Kafka【入门】看这一篇就够了!

[JavaGuide](javascript:void(0);) *10月10日*

以下文章来源于我没有三颗心脏 ，作者我没有三颗心脏

- [ ] ![我没有三颗心脏](http://wx.qlogo.cn/mmhead/Q3auHgzwzM5umlPqwbCtYl1GKt0FLJWFTvo4u87jxEtx1Q9UT6M0nQ/0)

我没有三颗心脏

记录&分享自己的学习和生活



> 前言：在之前的文章里面已经了解到了[「消息队列」](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247484794&idx=1&sn=61585fe69eedb3654ee2f9c9cd67e1a1&chksm=cea24ab1f9d5c3a7fd07dd49244f69fc85a5a523ee5353fc9e442dcad2ca0dd1137ed563fcbe&token=1082669959&lang=zh_CN&scene=21#wechat_redirect)是怎么样的一种存在（传送门），Kafka 作为当下流行的一种中间件，我们现在开始学习它！

# 一、Kafka 简介

------

## Kafka 创建背景

**Kafka** 是一个消息系统，原本开发自 LinkedIn，用作 LinkedIn 的活动流（Activity Stream）和运营数据处理管道（Pipeline）的基础。现在它已被多家不同类型的公司 作为多种类型的数据管道和消息系统使用。

**活动流数据**是几乎所有站点在对其网站使用情况做报表时都要用到的数据中最常规的部分。活动数据包括页面访问量（Page View）、被查看内容方面的信息以及搜索情况等内容。这种数据通常的处理方式是先把各种活动以日志的形式写入某种文件，然后周期性地对这些文件进行统计分析。**运营数据**指的是服务器的性能数据（CPU、IO 使用率、请求时间、服务日志等等数据)。运营数据的统计方法种类繁多。

近年来，活动和运营数据处理已经成为了网站软件产品特性中一个至关重要的组成部分，这就需要一套稍微更加复杂的基础设施对其提供支持。

## Kafka 简介

Kafka 是一种分布式的，基于发布 / 订阅的消息系统。主要设计目标如下：

- 以时间复杂度为 O(1) 的方式提供消息持久化能力，即使对 TB 级以上数据也能保证常数时间复杂度的访问性能。
- 高吞吐率。即使在非常廉价的商用机器上也能做到单机支持每秒 100K 条以上消息的传输。
- 支持 Kafka Server 间的消息分区，及分布式消费，同时保证每个 Partition 内的消息顺序传输。
- 同时支持离线数据处理和实时数据处理。
- Scale out：支持在线水平扩展。

## Kafka 基础概念

### 概念一：生产者与消费者

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6A7pYWqQvSo7SIdicMetETicSGbK8mKFDdAmIyWCO6ibZFYxhuFrC16gJwg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

对于 Kafka 来说客户端有两种基本类型：**生产者（Producer）**和**消费者（Consumer）**。除此之外，还有用来做数据集成的 Kafka Connect API 和流式处理的 Kafka Streams 等高阶客户端，但这些高阶客户端底层仍然是生产者和消费者API，它们只不过是在上层做了封装。

这很容易理解，生产者（也称为发布者）创建消息，而消费者（也称为订阅者）负责消费or读取消息。

### 概念二：主题（Topic）与分区（Partition）

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AWaskGRqjx9q0gp4ZorfvS1ptGWicTVoG0zZu3yULKmtgyXC5swDAVDA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

在 Kafka 中，消息以**主题（Topic）**来分类，每一个主题都对应一个「消息队列」，这有点儿类似于数据库中的表。但是如果我们把所有同类的消息都塞入到一个“中心”队列中，势必缺少可伸缩性，无论是生产者/消费者数目的增加，还是消息数量的增加，都可能耗尽系统的性能或存储。

我们使用一个生活中的例子来说明：现在 A 城市生产的某商品需要运输到 B 城市，走的是公路，那么单通道的高速公路不论是在「A 城市商品增多」还是「现在 C 城市也要往 B 城市运输东西」这样的情况下都会出现「吞吐量不足」的问题。所以我们现在引入**分区（Partition）**的概念，类似“允许多修几条道”的方式对我们的主题完成了水平扩展。

### 概念三：Broker 和集群（Cluster）

一个 Kafka 服务器也称为 Broker，它接受生产者发送的消息并存入磁盘；Broker 同时服务消费者拉取分区消息的请求，返回目前已经提交的消息。使用特定的机器硬件，一个 Broker 每秒可以处理成千上万的分区和百万量级的消息。（现在动不动就百万量级..我特地去查了一把，好像确实集群的情况下吞吐量挺高的..摁..）

若干个 Broker 组成一个集群（Cluster），其中集群内某个 Broker 会成为集群控制器（Cluster Controller），它负责管理集群，包括分配分区到 Broker、监控 Broker 故障等。在集群内，一个分区由一个 Broker 负责，这个 Broker 也称为这个分区的 Leader；当然一个分区可以被复制到多个 Broker 上来实现冗余，这样当存在 Broker 故障时可以将其分区重新分配到其他 Broker 来负责。下图是一个样例：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AiaicmVhfW1bjZej2WbJHJtw70iaLIgBWZO8gUCjEU6vpMnHGqUzQqrfKg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

Kafka 的一个关键性质是日志保留（retention），我们可以配置主题的消息保留策略，譬如只保留一段时间的日志或者只保留特定大小的日志。当超过这些限制时，老的消息会被删除。我们也可以针对某个主题单独设置消息过期策略，这样对于不同应用可以实现个性化。

### 概念四：多集群

随着业务发展，我们往往需要多集群，通常处于下面几个原因：

- 基于数据的隔离；
- 基于安全的隔离；
- 多数据中心（容灾）

当构建多个数据中心时，往往需要实现消息互通。举个例子，假如用户修改了个人资料，那么后续的请求无论被哪个数据中心处理，这个更新需要反映出来。又或者，多个数据中心的数据需要汇总到一个总控中心来做数据分析。

上面说的分区复制冗余机制只适用于同一个 Kafka 集群内部，对于多个 Kafka 集群消息同步可以使用 Kafka 提供的 MirrorMaker 工具。本质上来说，MirrorMaker 只是一个 Kafka 消费者和生产者，并使用一个队列连接起来而已。它从一个集群中消费消息，然后往另一个集群生产消息。

# 二、Kafka 的设计与实现

------

上面我们知道了 Kafka 中的一些基本概念，但作为一个成熟的「消息队列」中间件，其中有许多有意思的设计值得我们思考，下面我们简单列举一些。

## 讨论一：Kafka 存储在文件系统上

是的，**您首先应该知道 Kafka 的消息是存在于文件系统之上的**。Kafka 高度依赖文件系统来存储和缓存消息，一般的人认为 “磁盘是缓慢的”，所以对这样的设计持有怀疑态度。实际上，磁盘比人们预想的快很多也慢很多，这取决于它们如何被使用；一个好的磁盘结构设计可以使之跟网络速度一样快。

现代的操作系统针对磁盘的读写已经做了一些优化方案来加快磁盘的访问速度。比如，**预读**会提前将一个比较大的磁盘快读入内存。**后写**会将很多小的逻辑写操作合并起来组合成一个大的物理写操作。并且，操作系统还会将主内存剩余的所有空闲内存空间都用作**磁盘缓存**，所有的磁盘读写操作都会经过统一的磁盘缓存（除了直接 I/O 会绕过磁盘缓存）。综合这几点优化特点，**如果是针对磁盘的顺序访问，某些情况下它可能比随机的内存访问都要快，甚至可以和网络的速度相差无几。**

**上述的 Topic 其实是逻辑上的概念，面相消费者和生产者，物理上存储的其实是 Partition**，每一个 Partition 最终对应一个目录，里面存储所有的消息和索引文件。默认情况下，每一个 Topic 在创建时如果不指定 Partition 数量时只会创建 1 个 Partition。比如，我创建了一个 Topic 名字为 test ，没有指定 Partition 的数量，那么会默认创建一个 test-0 的文件夹，这里的命名规则是：`<topic_name>-<partition_id>`。

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AmT8ibibqx7yhlpQ6neImgU7E8jO20EOwMNM4g7Kic3L0KvNhW8brk2JNg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

任何发布到 Partition 的消息都会被追加到 Partition 数据文件的尾部，这样的顺序写磁盘操作让 Kafka 的效率非常高（经验证，顺序写磁盘效率比随机写内存还要高，这是 Kafka 高吞吐率的一个很重要的保证）。

每一条消息被发送到 Broker 中，会根据 Partition 规则选择被存储到哪一个 Partition。如果 Partition 规则设置的合理，所有消息可以均匀分布到不同的 Partition中。

## 讨论二：Kafka 中的底层存储设计

假设我们现在 Kafka 集群只有一个 Broker，我们创建 2 个 Topic 名称分别为：「topic1」和「topic2」，Partition 数量分别为 1、2，那么我们的根目录下就会创建如下三个文件夹：

```
    | --topic1-0
    | --topic2-0
    | --topic2-1
```

在 Kafka 的文件存储中，同一个 Topic 下有多个不同的 Partition，每个 Partition 都为一个目录，而每一个目录又被平均分配成多个大小相等的 **Segment File** 中，Segment File 又由 index file 和 data file 组成，他们总是成对出现，后缀 ".index" 和 ".log" 分表表示 Segment 索引文件和数据文件。

现在假设我们设置每个 Segment 大小为 500 MB，并启动生产者向 topic1 中写入大量数据，topic1-0 文件夹中就会产生类似如下的一些文件：

```
    | --topic1-0
        | --00000000000000000000.index
        | --00000000000000000000.log
        | --00000000000000368769.index
        | --00000000000000368769.log
        | --00000000000000737337.index
        | --00000000000000737337.log
        | --00000000000001105814.index
        | --00000000000001105814.log
    | --topic2-0
    | --topic2-1
```

**Segment 是 Kafka 文件存储的最小单位。**Segment 文件命名规则：Partition 全局的第一个 Segment 从 0 开始，后续每个 Segment 文件名为上一个 Segment 文件最后一条消息的 offset 值。数值最大为 64 位 long 大小，19 位数字字符长度，没有数字用0填充。如 00000000000000368769.index 和 00000000000000368769.log。

以上面的一对 Segment File 为例，说明一下索引文件和数据文件对应关系：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AcWLjPFn3ddxOyXMq9hbEpgwujAzjWTADoHOeVTdT62ibl3MJaiahrAjA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

其中以索引文件中元数据 `<3, 497>` 为例，依次在数据文件中表示第 3 个 message（在全局 Partition 表示第 368769 + 3 = 368772 个 message）以及该消息的物理偏移地址为 497。

注意该 index 文件并不是从0开始，也不是每次递增1的，这是因为 Kafka 采取稀疏索引存储的方式，每隔一定字节的数据建立一条索引，它减少了索引文件大小，使得能够把 index 映射到内存，降低了查询时的磁盘 IO 开销，同时也并没有给查询带来太多的时间消耗。

因为其文件名为上一个 Segment 最后一条消息的 offset ，所以当需要查找一个指定 offset 的 message 时，通过在所有 segment 的文件名中进行二分查找就能找到它归属的 segment ，再在其 index 文件中找到其对应到文件上的物理位置，就能拿出该 message 。

由于消息在 Partition 的 Segment 数据文件中是顺序读写的，且消息消费后不会删除（删除策略是针对过期的 Segment 文件），这种顺序磁盘 IO 存储设计师 Kafka 高性能很重要的原因。

> Kafka 是如何准确的知道 message 的偏移的呢？这是因为在 Kafka 定义了标准的数据存储结构，在 Partition 中的每一条 message 都包含了以下三个属性：
>
> - offset：表示 message 在当前 Partition 中的偏移量，是一个逻辑上的值，唯一确定了 Partition 中的一条 message，可以简单的认为是一个 id；
> - MessageSize：表示 message 内容 data 的大小；
> - data：message 的具体内容

## 讨论三：生产者设计概要

当我们发送消息之前，先问几个问题：每条消息都是很关键且不能容忍丢失么？偶尔重复消息可以么？我们关注的是消息延迟还是写入消息的吞吐量？

举个例子，有一个信用卡交易处理系统，当交易发生时会发送一条消息到 Kafka，另一个服务来读取消息并根据规则引擎来检查交易是否通过，将结果通过 Kafka 返回。对于这样的业务，消息既不能丢失也不能重复，由于交易量大因此吞吐量需要尽可能大，延迟可以稍微高一点。

再举个例子，假如我们需要收集用户在网页上的点击数据，对于这样的场景，少量消息丢失或者重复是可以容忍的，延迟多大都不重要只要不影响用户体验，吞吐则根据实时用户数来决定。

不同的业务需要使用不同的写入方式和配置。具体的方式我们在这里不做讨论，现在先看下生产者写消息的基本流程：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AD3ghaYFy4k15A89uDdcWNHHf62aJQ65ziaOfRTNaeiaoBic1LdoabZEiaw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

- 图片来源：http://www.dengshenyu.com/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F/2017/11/12/kafka-producer.html

流程如下：

1. 首先，我们需要创建一个ProducerRecord，这个对象需要包含消息的主题（topic）和值（value），可以选择性指定一个键值（key）或者分区（partition）。
2. 发送消息时，生产者会对键值和值序列化成字节数组，然后发送到分配器（partitioner）。
3. 如果我们指定了分区，那么分配器返回该分区即可；否则，分配器将会基于键值来选择一个分区并返回。
4. 选择完分区后，生产者知道了消息所属的主题和分区，它将这条记录添加到相同主题和分区的批量消息中，另一个线程负责发送这些批量消息到对应的Kafka broker。
5. 当broker接收到消息后，如果成功写入则返回一个包含消息的主题、分区及位移的RecordMetadata对象，否则返回异常。
6. 生产者接收到结果后，对于异常可能会进行重试。

## 讨论四：消费者设计概要

### 消费者与消费组

假设这么个场景：我们从Kafka中读取消息，并且进行检查，最后产生结果数据。我们可以创建一个消费者实例去做这件事情，但如果生产者写入消息的速度比消费者读取的速度快怎么办呢？这样随着时间增长，消息堆积越来越严重。对于这种场景，我们需要增加多个消费者来进行水平扩展。

Kafka消费者是**消费组**的一部分，当多个消费者形成一个消费组来消费主题时，每个消费者会收到不同分区的消息。假设有一个T1主题，该主题有4个分区；同时我们有一个消费组G1，这个消费组只有一个消费者C1。那么消费者C1将会收到这4个分区的消息，如下所示：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6ABO85XKJict9N3cDzUlfeOJ8ia0sUR0SL7UZ3VyiaFKu3XfngcITw8GdOg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如果我们增加新的消费者C2到消费组G1，那么每个消费者将会分别收到两个分区的消息，如下所示：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6ACDF3jvu286B3teO1o28EwjSSTYbZp5PW66KrpXkB2GclgXibjicCFXRg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

如果增加到4个消费者，那么每个消费者将会分别收到一个分区的消息，如下所示：

![img](data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVQImWNgYGBgAAAABQABh6FO1AAAAABJRU5ErkJggg==)

但如果我们继续增加消费者到这个消费组，剩余的消费者将会空闲，不会收到任何消息：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AGCkk27W2tI6coYxc8aCyomYSRCHiaTyM43NmuqPKKqoIPb3jwmzY5Kw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

总而言之，我们可以通过增加消费组的消费者来进行水平扩展提升消费能力。这也是为什么建议创建主题时使用比较多的分区数，这样可以在消费负载高的情况下增加消费者来提升性能。另外，消费者的数量不应该比分区数多，因为多出来的消费者是空闲的，没有任何帮助。

**Kafka一个很重要的特性就是，只需写入一次消息，可以支持任意多的应用读取这个消息。**换句话说，每个应用都可以读到全量的消息。为了使得每个应用都能读到全量消息，应用需要有不同的消费组。对于上面的例子，假如我们新增了一个新的消费组G2，而这个消费组有两个消费者，那么会是这样的：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AQiaZAWM8127z8RHl93BJ5MlUjW90627dDv8vqAhIVXbeG0gPhz64r4Q/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

在这个场景中，消费组G1和消费组G2都能收到T1主题的全量消息，在逻辑意义上来说它们属于不同的应用。

最后，总结起来就是：如果应用需要读取全量消息，那么请为该应用设置一个消费组；如果该应用消费能力不足，那么可以考虑在这个消费组里增加消费者。

### 消费组与分区重平衡

可以看到，当新的消费者加入消费组，它会消费一个或多个分区，而这些分区之前是由其他消费者负责的；另外，当消费者离开消费组（比如重启、宕机等）时，它所消费的分区会分配给其他分区。这种现象称为**重平衡（rebalance）**。重平衡是 Kafka 一个很重要的性质，这个性质保证了高可用和水平扩展。**不过也需要注意到，在重平衡期间，所有消费者都不能消费消息，因此会造成整个消费组短暂的不可用。**而且，将分区进行重平衡也会导致原来的消费者状态过期，从而导致消费者需要重新更新状态，这段期间也会降低消费性能。后面我们会讨论如何安全的进行重平衡以及如何尽可能避免。

消费者通过定期发送心跳（hearbeat）到一个作为组协调者（group coordinator）的 broker 来保持在消费组内存活。这个 broker 不是固定的，每个消费组都可能不同。当消费者拉取消息或者提交时，便会发送心跳。

如果消费者超过一定时间没有发送心跳，那么它的会话（session）就会过期，组协调者会认为该消费者已经宕机，然后触发重平衡。可以看到，从消费者宕机到会话过期是有一定时间的，这段时间内该消费者的分区都不能进行消息消费；通常情况下，我们可以进行优雅关闭，这样消费者会发送离开的消息到组协调者，这样组协调者可以立即进行重平衡而不需要等待会话过期。

在 0.10.1 版本，Kafka 对心跳机制进行了修改，将发送心跳与拉取消息进行分离，这样使得发送心跳的频率不受拉取的频率影响。另外更高版本的 Kafka 支持配置一个消费者多长时间不拉取消息但仍然保持存活，这个配置可以避免活锁（livelock）。活锁，是指应用没有故障但是由于某些原因不能进一步消费。

### Partition 与消费模型

上面提到，Kafka 中一个 topic 中的消息是被打散分配在多个 Partition(分区) 中存储的， Consumer Group 在消费时需要从不同的 Partition 获取消息，那最终如何重建出 Topic 中消息的顺序呢？

答案是：没有办法。Kafka 只会保证在 Partition 内消息是有序的，而不管全局的情况。

下一个问题是：Partition 中的消息可以被（不同的 Consumer Group）多次消费，那 Partition中被消费的消息是何时删除的？Partition 又是如何知道一个 Consumer Group 当前消费的位置呢？

无论消息是否被消费，除非消息到期 Partition 从不删除消息。例如设置保留时间为 2 天，则消息发布 2 天内任何 Group 都可以消费，2 天后，消息自动被删除。
Partition 会为每个 Consumer Group 保存一个偏移量，记录 Group 消费到的位置。如下图：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AZGQ8YFpRdQ0ric7AyoXR3j8DIExXJXlG1oibhdM6ia6aYkynxm4lLNpGw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### 为什么  Kafka 是 pull 模型

消费者应该向 Broker 要数据（pull）还是 Broker 向消费者推送数据（push）？作为一个消息系统，Kafka 遵循了传统的方式，选择由 Producer 向 broker push 消息并由 Consumer 从 broker pull 消息。一些 logging-centric system，比如 Facebook 的Scribe和 Cloudera 的Flume，采用 push 模式。事实上，push 模式和 pull 模式各有优劣。

**push 模式很难适应消费速率不同的消费者，因为消息发送速率是由 broker 决定的。**push 模式的目标是尽可能以最快速度传递消息，但是这样很容易造成 Consumer 来不及处理消息，典型的表现就是拒绝服务以及网络拥塞。**而 pull 模式则可以根据 Consumer 的消费能力以适当的速率消费消息。**

**对于 Kafka 而言，pull 模式更合适。**pull 模式可简化 broker 的设计，Consumer 可自主控制消费消息的速率，同时 Consumer 可以自己控制消费方式——即可批量消费也可逐条消费，同时还能选择不同的提交方式从而实现不同的传输语义。

## 讨论五：Kafka 如何保证可靠性

当我们讨论**可靠性**的时候，我们总会提到*保证**这个词语。可靠性保证是基础，我们基于这些基础之上构建我们的应用。比如关系型数据库的可靠性保证是ACID，也就是原子性（Atomicity）、一致性（Consistency）、隔离性（Isolation）和持久性（Durability）。

Kafka 中的可靠性保证有如下四点：

- 对于一个分区来说，它的消息是有序的。如果一个生产者向一个分区先写入消息A，然后写入消息B，那么消费者会先读取消息A再读取消息B。
- 当消息写入所有in-sync状态的副本后，消息才会认为**已提交（committed）**。这里的写入有可能只是写入到文件系统的缓存，不一定刷新到磁盘。生产者可以等待不同时机的确认，比如等待分区主副本写入即返回，后者等待所有in-sync状态副本写入才返回。
- 一旦消息已提交，那么只要有一个副本存活，数据不会丢失。
- 消费者只能读取到已提交的消息。

使用这些基础保证，我们构建一个可靠的系统，这时候需要考虑一个问题：究竟我们的应用需要多大程度的可靠性？可靠性不是无偿的，它与系统可用性、吞吐量、延迟和硬件价格息息相关，得此失彼。因此，我们往往需要做权衡，一味的追求可靠性并不实际。

> 想了解更多戳这里：http://www.dengshenyu.com/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F/2017/11/21/kafka-data-delivery.html

# 三、动手搭一个 Kafka

------

通过上面的描述，我们已经大致了解到了「Kafka」是何方神圣了，现在我们开始尝试自己动手本地搭一个来实际体验一把。

## 第一步：下载 Kafka

这里以 Mac OS 为例，在安装了 Homebrew 的情况下执行下列代码：

```
brew install kafka
```

由于 Kafka 依赖了 Zookeeper，所以在下载的时候会自动下载。

## 第二步：启动服务

我们在启动之前首先需要修改 Kafka 的监听地址和端口为 `localhost:9092`：

```
vi /usr/local/etc/kafka/server.properties
```

然后修改成下图的样子：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6A9K51WrHJGQUAWls38vNTdKAtTdhk9eVCTRLayZVXUUh977bcib28BeA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

依次启动 Zookeeper 和 Kafka：

```
brew services start zookeeper
brew services start kafka
```

然后执行下列语句来创建一个名字为 "test" 的 Topic：

```
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

我们可以通过下列的命令查看我们的 Topic 列表：

```
kafka-topics --list --zookeeper localhost:2181
```

## 第三步：发送消息

然后我们新建一个控制台，运行下列命令创建一个消费者关注刚才创建的 Topic：

```
kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning
```

用控制台往刚才创建的 Topic 中添加消息，并观察刚才创建的消费者窗口：

```
kafka-console-producer --broker-list localhost:9092 --topic test
```

能通过消费者窗口观察到正确的消息：

![img](https://mmbiz.qpic.cn/mmbiz_png/ia1kbU3RS1H4ZCa61G1hwan0ktD33VA6AUjmf0ibUzvz91Vxg4ndsS2IzBTvKtaH3iayTThWE9LVD6iakuo3T4dJgQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

# 参考资料

------

1. https://www.infoq.cn/article/kafka-analysis-part-1 - Kafka 设计解析（一）：Kafka 背景及架构介绍
2. http://www.dengshenyu.com/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F/2017/11/06/kafka-Meet-Kafka.html - Kafka系列（一）初识Kafka
3. https://lotabout.me/2018/kafka-introduction/ - Kafka 入门介绍
4. https://www.zhihu.com/question/28925721 - Kafka 中的 Topic 为什么要进行分区? - 知乎
5. https://blog.joway.io/posts/kafka-design-practice/ - Kafka 的设计与实践思考
6. http://www.dengshenyu.com/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F/2017/11/21/kafka-data-delivery.html - Kafka系列（六）可靠的数据传输

**推荐阅读**

[架构设计--互联网架构演化](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485613&idx=1&sn=29af9be51654779b54f73315b3ba2c64&chksm=cea24766f9d5ce7011233f3a0634b608e41cc6bc5a0a0afe836ad57efe08f7a4bb9c5495fc85&token=287192206&lang=zh_CN&scene=21#wechat_redirect)

[值得开发者关注的 Java 8 后时代的语言特性](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485609&idx=1&sn=a2e8133289058d8a16176d34b1683e21&chksm=cea24762f9d5ce742b1e82bd268a871ce7a67da5bba1c47b3b02667eb34136e0374aa9274a9e&token=287192206&lang=zh_CN&scene=21#wechat_redirect)

[【原创】关于 Spring 中的参数校验的一点思考](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485596&idx=2&sn=b3d193629399df604fbe527e08bb591f&chksm=cea24757f9d5ce41df368c345428bf972954667b15ccc9ba9f68376fd1b8b50b7be0d8e4ec40&token=69811960&lang=zh_CN&scene=21#wechat_redirect)

[Spring 常见问题总结（补充版）](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485576&idx=1&sn=f993349f12650a68904e1d99d2465131&chksm=cea24743f9d5ce55ffe543a0feaf2c566382024b625b59283482da6ab0cbcf2a3c9dc5b64a53&token=2133161636&lang=zh_CN&scene=21#wechat_redirect)

[分布式 ID 生成方案总结](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485590&idx=2&sn=9697a9fae7f4d319e2e92103186a2c26&chksm=cea2475df9d5ce4bb69770ce4934537db6bb1346457c861d51b0d18c130b0d66d35c2c17bc20&token=69811960&lang=zh_CN&scene=21#wechat_redirect)

[入职一个月的职场小白,谈谈自己这段时间的感受](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485540&idx=1&sn=4492eece8f7738e99350118040e14a79&chksm=cea247aff9d5ceb9e7c67f418d8a8518c550fd7dd269bf2c9bdef83309502273b4b9f1e7021f&token=1333232257&lang=zh_CN&scene=21#wechat_redirect)

[Code Review 最佳实践](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485494&idx=1&sn=c7f160fd1bb13a20b887493003acbbf9&chksm=cea247fdf9d5ceebf3b98bd7c524d0ecc672d4bcb10a70fae90390abd862538aa3283c93375b&token=1701499214&lang=zh_CN&scene=21#wechat_redirect)

[后端开发必备的 RestFul API 知识](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485510&idx=1&sn=e9273322ae638c8465a606737109ab97&chksm=cea2478df9d5ce9b58b9ff1f1e2ecca99e961b911adcec3d5a579b41e01151160cfb2891d91b&token=1701499214&lang=zh_CN&scene=21#wechat_redirect)

[技术面试复习大纲](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485505&idx=1&sn=f7d916334c078bc3fdc2933f889b5016&chksm=cea2478af9d5ce9cafcfe9a053e49e84296d8b1929f79844bba59c8c3d8b56753f34a2c2f6a9&token=1701499214&lang=zh_CN&scene=21#wechat_redirect)

[如何给老婆解释什么是 RPC](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485522&idx=1&sn=5ddce4bc51f024801d41af138e0d3229&chksm=cea24799f9d5ce8f31a17177f2c400f9a6d5bb684220b2de6981816e3da5b9cb7b9bc9edc278&token=1701499214&lang=zh_CN&scene=21#wechat_redirect)

[干货收藏 | Java 程序员必备的一些流程图](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485490&idx=1&sn=4470a0b4bb8b9c33dfd2bafb787e4429&chksm=cea247f9f9d5ceefeb721da34c70a8f828658656cfe35d8a6b7b2fd60d6b4a5d6377a28ba2bc&token=522128191&lang=zh_CN&scene=21#wechat_redirect)

[十分钟搞懂 Java 效率工具 Lombok 使用与原理](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485385&idx=2&sn=a7c3fb4485ffd8c019e5541e9b1580cd&chksm=cea24802f9d5c1144eee0da52cfc0cc5e8ee3590990de3bb642df4d4b2a8cd07f12dd54947b9&token=913106598&lang=zh_CN&scene=21#wechat_redirect)

[看完这篇文章，别说自己不会用 Lambda 表达式了！](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485425&idx=1&sn=3cc01bc7c42549b6b6aa62b3656e02d1&chksm=cea2483af9d5c12cd10174dac4465a631b14a6d6a09495b018a98e01c698e86368d26b3be03d&token=882315326&lang=zh_CN&scene=21#wechat_redirect)

[一些有助于你拿 Offer 的文章](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485434&idx=1&sn=f6bdf19d2594bf719e149e48d1384340&chksm=cea24831f9d5c1278617d347238f65f0481f36291675f05fabb382b69ea0ff3adae7ee6e6524&token=882315326&lang=zh_CN&scene=21#wechat_redirect)

[面试官:“谈谈 Spring 中都用到了那些设计模式?”。](https://mp.weixin.qq.com/s?__biz=Mzg2OTA0Njk0OA==&mid=2247485303&idx=1&sn=9e4626a1e3f001f9b0d84a6fa0cff04a&chksm=cea248bcf9d5c1aaf48b67cc52bac74eb29d6037848d6cf213b0e5466f2d1fda970db700ba41&token=1229383574&lang=zh_CN&scene=21#wechat_redirect)







如果大家想要实时关注我更新的文章以及分享的干货的话，可以关注我的公众号。

![img](https://mmbiz.qpic.cn/mmbiz_jpg/iaIdQfEric9TxJKr58OIT9CEosE2xs5VsK3QdkOc2ES1yDP72Eicw6Yj7lLfwU9f2JasBc2oFqCmVe5EOj6g5Un1A/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

欢迎关注![img](https://mmbiz.qpic.cn/mmbiz_png/iaIdQfEric9TxAghDEtyL0yGWb5bwkkbojspAFsIK81p9GbZ7ibpE1PM7HSBxvSm0BjwYa4v6JIlMIhYMMEDeOdUg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)点个再看![img](https://mmbiz.qpic.cn/mmbiz_png/iaIdQfEric9TyKb57lE0g04qWvTs2hykJYtD2NElGSPQXZwyYVJCRC6vIzAI3L0LvEYFRibC3BjLwbmibvev2lniadA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)