redis安装

yum install gcc

wget http://download.redis.io/releases/redis-5.0.4.tar.gz

tar zxvf redis-5.0.4.tar.gz

cd redis-5.0.4

make MALLOC=libc

  

redis启动方式

1、修改redis.conf文件 daemonize为yes
2、然后使用redis.conf启动
cd redis-5.0.4/
./redis-server /usr/local/redis-5.0.4/redis.conf


centos7下使用yum安装Redis

第一步：安装
yum –y install redis
第二步：启动
systemctl start redis.service
第三步：设置开机启动
systemctl enable redis.service
可选步骤
设置密码
打开/etc/redis.conf
vim /etc/redis.conf
设置密码为123456
# requirepass foobared
requirepass 123456
保存退出
重启
systemctl restart redis.service
开放远程权限
打开/etc/redis.conf
vim /etc/redis.conf
开放远程访问权限
bind 127.0.0.1
修改为
bind 0.0.0.0
重启
systemctl restart redis.service
