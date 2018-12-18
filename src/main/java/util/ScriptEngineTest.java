package util;

/**
 * Created by wsig on 2018-12-17.
 */

import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/** 直接调用js代码 */
public class ScriptEngineTest {
  public static void main(String[] args) {
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("javascript");
    try {
      engine.eval("var a=3; var b=4;print (a+b);");
    } catch (ScriptException e) {
      e.printStackTrace();
    }

    try {
      engine.put("a", 4);
      engine.put("b", 3);
      Double result = (Double) engine.eval("a+b");
      System.out.println("result = " + result);
      engine.eval("c=a+b");
      Double c = (Double) engine.get("c");
      System.out.println("c = " + c);
    } catch (ScriptException e) {
      e.printStackTrace();
    }

    try {
      String jsFileName = "ScriptEngineTest.js";
      FileReader reader = new FileReader(jsFileName);
      engine.eval(reader);
      if (engine instanceof Invocable) {
        Invocable invoke = (Invocable) engine;
        // c = merge(2, 3);
        Double c = (Double) invoke.invokeFunction("merge", 2, 3);
        System.out.println("c = " + c);
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    //try {
    //  //jython.jar jsr223
    //  String code = "";
    //  //js代码
    //  ScriptEngineManager factory = new ScriptEngineManager();
    //  ScriptEngineManager scriptEngine = factory.getEngineByName("javascript");
    //  scriptEngine.eval(code);
    //} catch (Exception e) {
    //  e.printStackTrace();
    //}
  }
}
