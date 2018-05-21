/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appguru.extensions;

import appguru.AGE;
import appguru.helper.FileHelper;
import appguru.helper.Tuple;
import appguru.info.Console;
import appguru.timer.TimeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 *
 * @author lars
 */
public class ExtensionLoader {

    public static ScriptEngine NASHORN = new ScriptEngineManager().getEngineByName("nashorn");
    public static PythonInterpreter PYI = new PythonInterpreter();
    public static Globals GLOBALS;
    public static Runtime RUNTIME = Runtime.getRuntime();

    public static boolean executePythonExtension(String ext, Tuple... args) {
        String s = FileHelper.readFile(ext);
        if (s == null) {
            Console.errorMessage(ExtensionLoader.class.getName(), "COULDNT FIND SOURCE OF PYTHON EXTENSION " + ext);
            return false;
        }
        PYI.cleanup();
        for (Tuple t : args) {
            PYI.set((String) t.value1, t.value2);
        }
        PYI.set("EXTENSION_PATH", ext);
        PYI.set("EXTENSION_DIR", new File(ext).getParent());
        PYI.exec(s);
        PyObject i = PYI.get("INTERVAL");
        /*AGE.global_timer.addEvent(i.asInt(), new TimeListener() {
            @Override
            public void invoke(long arg) {
                PYI.exec("SYNCHRONIZED()");
            }
        });*/
        Object a = PYI.get("STATUS");
        String state = (a == null ? "NOT AVAILABLE" : a.toString());
        Console.successMessage(ExtensionLoader.class.getName(), "LOADED PYTHON EXTENSION " + ext + ", STATE REPORT : " + state);
        //dostuff
        return true;
    }

    public static boolean executeLuaExtension(String ext, Tuple... args) {
        String s = FileHelper.readFile(ext);
        if (s == null) {
            Console.errorMessage(ExtensionLoader.class.getName(), "COULDNT FIND SOURCE OF LUA EXTENSION " + ext);
            return false;
        }
        GLOBALS = JsePlatform.standardGlobals();
        for (Tuple t : args) {
            GLOBALS.set((String) t.value1, LuaValue.userdataOf(t.value2));
        }
        GLOBALS.set("EXTENSION_PATH", ext);
        LuaValue script = GLOBALS.load(s);
        script.call(LuaValue.valueOf(s));
        LuaValue a = GLOBALS.get("STATUS");
        String state = (a == LuaValue.NIL ? "NOT AVAILABLE" : a.toString());
        Console.successMessage(ExtensionLoader.class.getName(), "LOADED LUA EXTENSION " + ext + ", STATE REPORT : " + state);
        //dostuff
        return true;
    }

    public static boolean executeJSExtension(String ext, Tuple... args) {
        String s = FileHelper.readFile(ext);
        if (s == null) {
            Console.errorMessage(ExtensionLoader.class.getName(), "COULDNT FIND SOURCE OF JAVASCRIPT EXTENSION " + ext);
            return false;
        }
        for (Tuple t : args) {
            NASHORN.put((String) t.value1, t.value2);
        }
        NASHORN.put("EXTENSION_PATH", ext);
        try {
            NASHORN.eval(s);
        } catch (ScriptException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "CORRUPTED JAVASCRIPT SCRIPT" + ext);
            return false;
        }
        Object a = NASHORN.get("STATUS");
        String state = (a == null ? "NOT AVAILABLE" : a.toString());
        Console.successMessage(ExtensionLoader.class.getName(), "LOADED JAVASCRIPT EXTENSION " + ext + ", STATE REPORT : " + state);
        //dostuff
        return true;
    }

    public static boolean compileJavaExtension(String ext, String... cmds) {
        try {
            Process compilation = RUNTIME.exec("javac " + ext, cmds);
            BufferedReader r = new BufferedReader(new InputStreamReader(compilation.getErrorStream()));
            String s = r.readLine();
            while (s != null) {
                Console.errorMessage(ExtensionLoader.class.getName(), r.readLine());
                s = r.readLine();
            }
            r = new BufferedReader(new InputStreamReader(compilation.getInputStream()));
            s = r.readLine();
            while (s != null) {
                Console.infoMessage(ExtensionLoader.class.getName(), r.readLine());
                s = r.readLine();
            }
            Console.successMessage(ExtensionLoader.class.getName(), "COMPILED JAVA CLASS " + ext);
            return true;
        } catch (IOException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "CORRUPTED FILE");
            return false;
        }
    }

    public static boolean executeJavaExtension(String ext) {
        try {
            URL[] thisfile = new URL[]{new File(ext).toURL()};
            URLClassLoader loader = new URLClassLoader(thisfile);
            Class<?> extension = Class.forName("appguru.extensions.java.SampleExtension", true, loader);
            extension.newInstance();
            //loader.loadClass("SampleExtension");
            Process execution = Runtime.getRuntime().exec(ext);
            BufferedReader r = new BufferedReader(new InputStreamReader(execution.getErrorStream()));
            String s = r.readLine();
            while (s != null) {
                Console.errorMessage(ExtensionLoader.class.getName(), r.readLine());
                s = r.readLine();
            }
            r = new BufferedReader(new InputStreamReader(execution.getInputStream()));
            s = r.readLine();
            while (s != null) {
                Console.infoMessage(ExtensionLoader.class.getName(), r.readLine());
                s = r.readLine();
            }
            execution.waitFor();
            Console.successMessage(ExtensionLoader.class.getName(), "EXECUTED JAVA CLASS " + ext);
        return true;
        } catch (IOException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "CORRUPTED FILE");
        } catch (InterruptedException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "THREAD INTERRUPTION");
        } catch (ClassNotFoundException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "CLASS NOT FOUND");
        } catch (InstantiationException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "COULD NOT CREATE CLASS");
        } catch (IllegalAccessException ex) {
            Console.errorMessage(ExtensionLoader.class.getName(), "COULD NOT ACCESS CLASS");
        } finally {
            return false;
        }
    }
}
