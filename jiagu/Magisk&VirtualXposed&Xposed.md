Magisk框架，Xposed框架，Vxp框架对比

1.支持平台
Magisk支持android5.0~8.1
Vxp框架支持android5.0~9.0，
Xposed支持android4.4以下，android5.0~8.1，但是8.0~8.1稳定版还没出来，出来的beta版本

2.模拟器，真机支持情况
Vxp框架不支持 x86，也就是不支持模拟器，只能使用真机，Magisk和Xposed对真机和模拟器都有支持

3.更新和稳定性
Magisk更新快一直在更新，最新更新是1天前更新的
Vxp框架一直在更新，最新更新19天前
Xposed已经停更了
Vxp和Magisk框架很年轻，模块数量还远不如Xposed框架那么丰富，Xposed更稳定
Vxp不会变砖

4.激活模块
Vxp支持免重启手机激活模块
Xposed和magisk必须重启激活模块

5.root
Magisk内置ROOT，不需要再安装SuperSU
Vxp免root
Xposed需要root

6.hook
Vxp暂不支持资源HOOK，部分插件的兼容性有问题，不能 hook 系统 API，使用必须将需要 hook 的 APP 和模块 APP
Magisk和Xposed支持hook

7.开源代码存放位置
Magisk：https://github.com/topjohnwu/Magisk
Vxp ：https://github.com/android-hacker/VirtualXposed
Xposed ： https://github.com/rovo89

8.原理
VirtualApp：它去启动别的App，在启动过程中通过 epic Hook本进程，从而控制被启动的App
Magisk：对系统侵入较少，仅修改boot.img，同时能够对系统隐藏自身存在，支持OTA升级，可以实现Multirom多系统等功能
Xposed：Xposed框架的原理是通过替换/system/bin/app_process程序控制zygote进程，使得app_process在启动过程中会加载XposedBridge.jar这个jar包，从而完成对Zygote进程及其创建的Dalvik或者art虚拟机的劫持