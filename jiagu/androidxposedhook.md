一、 HOOK JAVA 层函数的三要素获取：   目标类路径、 函数名、 参数 。
二、插件开发步骤
1. 以导入lib文件XposedBridgeApi-54.jar（不参与编译到最终文件中） 且用 AS 开发的你千万不要将其放在 libs
  AS中可以在配置文件配置 ：
                      provided 'de.robv.android.xposed:api:53'
                      provided 'de.robv.android.xposed:api:53:sources'

2. 在 AndroidManifest.xml 中添加框架信息：
  <application>
  <meta-data android:name="xposedmodule" android:value="true"/>
  <meta-data android:name="xposeddescription" android:value="这里填写模块说明信息"/>
  <meta-data android:name="xposedminversion" android:value="54"/>
  </application> 

3.编写响应类并实现类 IXposedHookLoadPackage 接口 handleLoadPackage 函数方法： 
  在 handleLoadPackage 函数的 findAndHookMethod 方法我们要提供 HOOK 目标的信息，
  参数为(类全路径,当前的 CLASSLOADER，HOOK 的函数名，参数 1 类类型...参数 N 类类型, XC_MethodHook 的回调) 
  XposedHelpers.findAndHookMethod(类全路径, loader, 方法, 参数, new XC_MethodHook(){
    ......
  }
  callMethod(类对象, 执行的方法，参数); // 主动执行想要hook的方法
  Object activityThread = callStaticMethod(findClass("android.app.ActivityThread", null), "currentActivityThread");

4. 将响应类添加到框架启动文件 
  新建 assests 文件夹， 并在其中新建 xposed_init 文件，写入插件入口类的信息 

5. 安装我们的插件设置启用，并在机器重启后插件生效 


以上步骤就可以完成简单的对app的hook了，但是对于某些参数例如 String[][] strAry, Map mp1, Map<Integer, String> mp3,
  ArrayList<String> al1, ArgClass ac 这些该怎么办呢？用反射调用的方式 可以看出
  String[][] ==> [[Ljava/lang/String;
  Map 数组不论何种形式 ==> Ljava/util/Map;
  ArrayList 无论何种形式 ==> Ljava/util/ArrayList;
  ArgClass ac 自定义类给个全路径的事==> Laqcxbom/xposedhooktarget/ArgClass; 


         import de.robv.android.xposed.IXposedHookLoadPackage;
         import de.robv.android.xposed.XC_MethodHook;
         import de.robv.android.xposed.XposedHelpers;
         import de.robv.android.xposed.callbacks.XC_LoadPackage;
         
         public class XposedTest implements IXposedHookLoadPackage {
         
           @Override public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam)
               throws Throwable {
             if (loadPackageParam.packageName.equals("package.name")) {
               System.out.println("found target: " + loadPackageParam.packageName);
               final Class<?> ArgClass =
                   XposedHelpers.findClass("path.ArgClass", loadPackageParam.classLoader);
               final Class<?> ArrayList =
                   XposedHelpers.findClass("java.util.ArrayList", loadPackageParam.classLoader);
               final Class<?> Map = XposedHelpers.findClass("java.util.Map", loadPackageParam.classLoader);
               XposedHelpers.findAndHookMethod("path.MyClass", loadPackageParam.classLoader, "method.name",
                   "[[Ljava.lang.String;", Map, ArrayList, ArgClass, new XC_MethodHook() {
                     @Override protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param)
                         throws Throwable {
                       super.beforeHookedMethod(param);
                     }
         
                     @Override protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                       super.afterHookedMethod(param);
                     }
                   });
             }
           }
         } 


