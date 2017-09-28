---
layout: post
title: 安卓学习笔记
---

准备工作：

1. 因为使用了 Github 私有库，需准备相关公钥并将公钥导入到 docker container 和 github。

启动：

#!/usr/bin/env bash
CWD=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

docker run -d -l pid=composer \
    -e TORAN_HOST=composer.web.dm \
    -v /data/toran-proxy:/data/toran-proxy \
    -v $CWD/ssh:/data/toran-proxy/ssh \
    cedvan/toran-proxy:1.1.7-1

验证：
docker exec -it $CID bash
php /var/www/bin/cron -v

系统升级：
docker exec -it $CID bash
php /var/www/app/console toran:update

最后：

整合到 nginx ，并设置域名为 composer.web.dm

FAQ:
composer 私有库无法自动更新?
1. 检查 github 公钥
docker exec -it $CID bash
php /var/www/bin/cron -v
