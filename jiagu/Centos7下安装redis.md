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