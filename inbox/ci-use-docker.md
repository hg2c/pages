# 用 Docker 交付软件

## 问题

现状无须赘述，在开发环境，测试，发布，回滚等环节都充满了痛苦和风险：

- 比如漏掉某个依赖或依赖对象不匹配；

- 比如回滚考虑的细节点太多，经常导致无法回滚，更无法做到自动回滚。


## 解决方案

    Goodbye DevOps，Hello ContainerOps

1. 软件交付包括开发代码，质管测试，预发布，上线等环节；
   
   其流转过程如同货物运输的卡车，火车，仓储，起重机，港口，和船队等环节。

2. 集装箱通过实现每个环节的标准化，极大提高了运输的效率并降低了成本；

   而 Docker 对于软件交付的价值，正如 Docker 的集装箱图标。


## 标准化交付的价值

- 开发环节：可以简单快速的构建起开发环境，并保持统一的开发环境。

- 构建环节：开发向测试交付版本时，再也不需要写繁琐的部署文档了，更重要的是，测试和开发可以保持一致的环境，避免了测试过程中由于环境不一致引入的“额外功”，也减少了测试人员部署系统的时间开销。

- 部署环节：从前运维人员拿到的一般是程序发布包，有了 Docker 之后，可以直接使用质管部门交付的 Docker 镜像，做到真正的秒级部署。

- 管理环节：由于 Docker 方式的快速部署、启动特性，在 scaling 具有原来方式无可比拟的优势。

- 自动化：标准化后，各环节更清晰简单，使得自动化运维成为可能。自动扩容，自动恢复，自动回滚都更容易实现。


## Q&A

### 0x01 - Docker 成熟吗？

1. 容器化技术是成熟的。第一个容器化技术是 1982 年的 chroot。Docker 的内核 LXC 2005 年诞生，LXC 的内核 Namespace 2008 年就已经进入 linux 内核（2.6.15）。

2. Docker 并不是一个新技术，而是将一系列技术有机的组合到一起，并提供极致的用户体验，就产生了垮时代意义的产品，成为近年最火的云项目。

### 0x02 - Docker 在生产环境足够稳定吗？

1. Docker 在镜像管理，发布管理，日志管理，配置管理，存储管理，网络管理等方面都已有成熟方案；
2. 假设 Docker 的可用性很低，只有 99%。但 Docker 可以轻易实现单机上千节点。通过大集群的方式，能极大的提高可用性。
3. 国内外已经有一批千万级用户公司，在生产环境全部使用了 Docker。
4. 个人经验，某线上项目，使用 12个 Docker 容器，已在生产环境稳定使用一年余。因负载过高，磁盘满出过两次问题，都通过“关闭老容器-启动新容器”实现了秒级恢复。

### 0x03 - Docker 安全吗？

结论：Docker 的安全性，取决于你如何使用。但不会比 VM 低。详情可见阿里的分析：
[Docker的安全性](https://yq.aliyun.com/articles/7590)

### 0x04 - Docker 有大公司在用吗？

Google, Facebook, 腾讯都已建立了大规模的 Docker 集群。就连 Windows Server 2016 也已经加入了对 Docker 的原生支持。

### 0x05 - 使用 Docker，会浪费对 OpenStack 已有的投资么？

正确看待 OpenStack、KVM、Docker 的方式应该是:

OpenStack 用于管理整个数据中心，KVM 和 Docker 作为相应的补充，KVM 用于多租户的计算资源管理，Docker 用于应用程序的打包部署。

在这种场景下，Docker的作用是：

1. Docker提供一种特定的软件打包方式，使得软件可以保持在相同的环境下运行。
2. Docker为微服务提供了很好的容器。
3. Docker在OpenStack、裸机上运行几乎一样。

总得来说，对于大部分的应用场景，使用那种云平台都可以。且 OpenStack Orchestration 工具 Heat 从 Icehouse 版本开始支持 Docker。

### 0x06 - Docker 与 KVM 的区别？

KVM 是把一辆大货车拆分成多辆小货车；

Docker 是标准化的集装箱。

Docker 本身不适合公有的多租户隔离，但是 Docker 的隔离及安全性用作企业内部的私有云隔离绰绰有余。同时，我们可以将 VM 与 Docker 结合使用，达到隔离性与性能的初衷。如有必要，也可以通过启用AppArmor、SELinux和GRSEC等加固解决方案，添加额外的安全层。

### 0x07 - Docker 的性能如何？

Docker 本质上是一个进程，没有虚拟化的开销，因此在各个资源维度上比虚拟机具有相当大的优势。（IBM等机构发表的报告已经证实这一点）


### 0x08 - Docker 对于开发者的价值

对于运维来说，Docker提供了一种可移植的标准化部署过程，使得规模化、自动化、异构化的部署成为可能甚至是轻松简单的事情；而对于开发者来说，Docker提供了一种开发环境的管理方法，包括映像、构建、共享等功能。

我们先看看程序员在搭建开发环境时遇到的一些问题：

- 软件安装麻烦，公司内部的标准os主要是运营os，没有针对开发环境做定制化。开发人员在初始化环境时，需要安装诸多依赖，造成团队内部每个人的环境都可能有很大不同。而且一般开发人员没有root，安装一个nginx或者是mysql都得自己下载编译安装 权限问题，没有root，一些软件无法运行，例如dnsmasq；

- 没有root，无法修改hosts，无法netstat -nptl，无法tcpdump，无法iptable

- 隔离性差，例如不同的开发人员如果在同一台主机环境下共享开发，虽然是用户隔离，但端口如果不规范可能会冲突；同一个Mysql如果权限管理不好很有可能误删别人的数据

- 可移植性差，例如和生产环境不一致，开发人员之间也无法共享；更严重的情况是当有新人入职时，通常需要又折腾一遍开发环境，无法快速搭建。

因此，在推广统一开发环境这件事情上，我也计划使用 Docker，通过大规模使用来储备 Docker 人才。

### 0x09 - 如何用 Docker 发布

![http://i.imgur.com/RKeXnmL.png](http://i.imgur.com/RKeXnmL.png)

### 0x10 - 如何解决回滚时的依赖问题

![http://i.imgur.com/ES8Bhqn.png](http://i.imgur.com/ES8Bhqn.png)

## 参考


- [Docker在生产环境的挑战和应对](http://www.infoq.com/cn/presentations/challenges-and-respond-of-docker-in-the-production-environment)

- [Docker On Gaia——腾讯Docker管理解决方案](http://data.qq.com/article?id=2634)

- [用Docker之后还需要OpenStack吗？](https://www.ustack.com/blog/do-i-need-docker-also-with-openstack/)