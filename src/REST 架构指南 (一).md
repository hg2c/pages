REST 是一种 web 服务组织架构，为建立扩展性良好的分布式系统而生。

《[Rest In Practice](https://www.amazon.com/gp/product/0596805829)》书中，将 REST 架构的实现程度，分为四个层次。

## 1
* * *
## Level 0: The Swamp of POX

**传输**：使用 HTTP 作为传输方式，比如通过网络将这篇文章发送到你的面前，为第一个层次。

SOAP、XML-RPC 等都属于这个层次。

## 2
* * *
## Level 1: Resources

**资源**：引入了资源的概念。和处于第一个层次的 RPC 相比较：

- RPC 是面向功能的架构，设计之初首先需要考虑的是提供怎样的功能；
- REST 是面向资源的架构，所以设计之初首要考虑的是有哪些资源可供操作。

## 3
* * *
## Level 2: HTTP Verbs

**动作**：这个层次和第二个层次相比，除了 GET，POST，还使用了 HTTP 的更多方法，比如 DELETE，PATCH 等，进一步统一了操作接口。

## 4
* * *
## Level 3: Hypermedia Controls

**HATEOAS**：到第四层才算是真正的 REST 架构。这个层次解决的是 service discoverablility 和 self documenting。

我们回到 REST 的定义：为建立扩展性良好的分布式系统而生。而这个世界上，规模最大的分布式系统，就是互联网本身。

互联网的一切都是资源。当我们想获取一个资源，比如购买一个东西时。我们只需知道一个互联网的入口，打开搜索引擎，找到购物网站，按网站的指引挑选商品，下单，支付。在这里互联网就如同一个状态机，告诉用户当前状态，以及下一步可以操作的东西，比如链接、按钮等等。引导我们完成每一步操作，直至获取到想要的资源。

HATEOAS 想做到的正是如此。REST 客户端只需要知道一个服务入口，就可以获取到和这个入口相关的，所有可用资源列表，以及对某种资源可以进行的操作。

- 每个资源都拥有一个资源标识，可以用来唯一地标明该资源。
- REST 系统中返回的信息，需描述资源自身的信息。例如 MIME 类型，是否可以被缓存等。
- REST 系统中返回的信息，需描述资源自身的处理方式。例如添加资源，更新资源的的操作链接，而不需要额外的文档进行说明。

```xml
例如：
GET /account/12345 HTTP/1.1
Host: bank.io
Accept: application/xml

返回：
<account>
  <account_number>12345</account_number>
  <balance currency="usd">100.00</balance>
  <link rel="存款" href="https://bank.io/account/12345/deposit" />
  <link rel="取款" href="https://bank.io/account/12345/withdraw" />
  <link rel="转账" href="https://bank.io/account/12345/transfer" />
</account>

而当账户透支时，可操作链接就只剩存款了：
<account>
  <account_number>12345</account_number>
  <balance currency="usd">-100.00</balance>
  <link rel="存款" href="https://bank.io/account/12345/deposit" />
</account>
```

实例如：[GitHub API](https://api.github.com/)
