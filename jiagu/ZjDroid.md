
adb shell logcat -s zjdroid-shell-com.fzisen.app51zxw

adb shell logcat -s zjdroid-apimonitor-com.fzisen.app51zxw

adb shell am broadcast -a com.zjdroid.invoke --ei target 6669 --es cmd '{action:dump_dexinfo}'

adb shell am broadcast -a com.zjdroid.invoke --ei target 6599 --es cmd '{"action":"backsmali", "dexpath":"/data/app/com.fzisen.app51zxw-1.apk"}'

adb shell am broadcast -a com.zjdroid.invoke --ei target 4095 --es cmd '{"action":"dump_class","dexpath":"/data/app/com.fzisen.app51zxw-1.apk"}'

am broadcast -a com.zjdroid.invoke --ei target 6249 --es cmd '{"action":"invoke","filepath":"/data/app/com.fzisen.app51zxw-1.apk"}'



        adb pull /data/data/com.fzisen.app51zxw/files/smali D:\DEV\AK47\demo\smali