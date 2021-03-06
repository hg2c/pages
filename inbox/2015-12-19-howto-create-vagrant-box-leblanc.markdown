---
layout: post
title:  "自制 Vagrant Box"
date:   2015-12-18 21:00:00
---

第一次 [Vagrant] Up 时, 会以 Vagrantfile 里配置的 [Vagrant Box] 为蓝本克隆一个新的虚拟机。

{% highlight ruby %}
Vagrant.configure("2") do |config|
  config.vm.box = "leblanc"
end
{% endhighlight %}

本文介绍如何自制一个名为 **leblanc** 的 Centos 7 Vagrant Box。


## 0x01 准备

1. 安装 Vagrant, VirtualBox：
```
brew cask install vagrant virtualbox
```

2. 克隆并进入 leblanc
```
git clone git@github.com:fpcdm/leblanc.git
cd leblanc
```

3. 如有必要更新 leblanc 版本号，如设置为 1.0.1：

```
echo 1.0.1 > .leblanc
```

4. 打开 VirtualBox, 新建 **leblanc_1.0.1** 虚拟机：

		VirtualBox settings changed from default
			NAME: lebl
			Type: Linux
			Version: Linux 2.6/3.x/4.x (64-bit)
			Motherboard->Base Memory: 1024
			Hard drive size: 128 GB dynamic VDI

			Disabled Audio
            eth1: NAT
            eth2: Host-Only

5. 设置虚拟机端口转发，设置 .ssh/config，启动虚拟机。

```
cd leblanc
make run
```

6. 安装 Centos 7。

		GUI installer changes from default:
			DATE & TIME set to Asia/Shanghai
			Disabled kdump
			Use auto LVM partitioning, UPDATE '/' to 32G, remain free space > 2G for docker
			NETWORK & HOST NAME set enp0s3 to on
			Root password set to 'vagrant'

            Begin INSTALL...

			Reboot

## 0x02 准备



#VAGRANT-BEGIN
# The contents below are automatically generated by Vagrant. Do not modify.
NM_CONTROLLED=no
BOOTPROTO=none
ONBOOT=yes
IPADDR=10.0.0.2
NETMASK=255.255.255.0
DEVICE=enp0s8
PEERDNS=no
#VAGRANT-END

避免冲突
Host zde
     Hostname 10.0.0.2
     User root
     IdentityFile ~/.zde/zbj@leblanc
     StrictHostKeyChecking no
     UserKnownHostsFile=/dev/null


7. 上传公钥，实现免密码登陆 leblanc：

```
brew install ssh-copy-id
ssh-copy-id -i ~/.ssh/zbj\@leblanc zde
```

## lebl

# 下行存疑
echo nameserver 172.30.200.8 > /etc/resolv.conf


## lebl-vbguest

cd shen
make zde --common
ssh zde vbguest

## lebl-ansible

cd shen
make zde

cd leblanc
make package
make publish









8. 设置LVM

fdisk /dev/sda
>n
>p
>3
>
>
>t
>3
>8e(linux LVM)
>w
分成一个分区，格式为linux LVM. 下面开始把分区加到 LVM 内去：

1. 建立物理卷
fdisk -l /dev/sda3
shen: make docker
<!-- partprobe -->

<!-- pvcreate /dev/sda3 -->
<!-- vgcreate cloud /dev/sda3 -->

<!-- 2.把新物理卷加入到卷组中去 -->
<!-- vgextend centos /dev/sda3 -->

<!-- INSTALL VBGuest -->
<!-- leblanc:/usr/local/sbin/vbguest -->



8. 安装相关软件：
```
git clone git@github.com:fpcdm/shen.git
cd shen
make leblanc

- hosts: development
  sudo: no
  gather_facts: no
  roles:
    - common*
    - repo.web.dm
    - docker
    - nginx
    - consul
    - consul-template
    - git
    - oh-my-zsh
    - tmux
    - golang

    #- vbguest
```

坑
1. ifcfg-dr0 造成的困扰，ifcfg-vr0
2. FIX: /etc/sysconfig/docker-storage
DOCKER_STORAGE_OPTIONS=

device-mapper table thin conldnt open thin internal device

3. .ssh/config
localhost

synchronize


## 0x02 打包

clean:
docker ps -aq | xargs docker rm -f


make dev publish

{% highlight bash %}
# 打包 ./package.box
vagrant package --base leblanc_1.0.1
# 将 ./package.box 导入 vagrant 并命令为 leblanc_1.0.1
vagrant box add leblanc_1.0.1 ./package.box

# 列出现有的 vagrant box
vagrant box list
{% endhighlight %}

现在就可以在 Vagrantfile 里使用 leblanc_1.0.1 了。


https://github.com/bbirkinbine/vagrant-centos-7-1503-x86_64-minimal
