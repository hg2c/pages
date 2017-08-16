#!/usr/bin/env bash
IMGDIR=assets/img
POSTDIR=src

mkdir -p $IMGDIR
mkdir -p $POSTDIR

cp -a ~/Documents/assets/img/spec.v2 $IMGDIR

cp ~/Documents/sahaba/容器云·镜像策略·运行时加载代码.md $POSTDIR/
cp ~/Documents/sahaba/容器云·发布和回滚.md $POSTDIR/
