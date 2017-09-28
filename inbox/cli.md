Linux rsync实现断点续传
rsync -rP --rsh=ssh /home/oracle/TestDB/ oracle@192.168.1.173:/home/oracle/TestDB


因为等待服务器回复是十分有用的“压力反转（backpressure，涉及IO优化，请自行搜索）”机制

#!/usr/bin/env bash
CWD=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
cd $CWD

VERSION=0.7.0
PACKAGE=consul_${VERSION}_web_ui.zip

URL=https://releases.hashicorp.com/consul/${VERSION}/${PACKAGE}

wget $URL

unzip $PACKAGE
echo $PACKAGE | awk -F'.zip' '{print "unzip "$0" -d "$1}' | sh

# from: https://www.consul.io/downloads.html
