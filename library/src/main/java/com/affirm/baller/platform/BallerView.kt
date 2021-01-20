package com.affirm.baller.platform

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.affirm.baller.service.NativeHost
import com.affirm.baller.service.NativeHttp
import com.affirm.baller.service.NativeStore
import com.affirm.baller.utils.*
import com.affirm.baller.view.NativeView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

open class BallerView constructor(context: Context, scaledWidth: Int) : LayoutView(context) {

    interface BallerViewInterface {
        fun  onEvent(name: String, value: String);
    }

    var _scaledWidth: Int = scaledWidth;
    var _nv: NativeView? = null;
    var _delegate: BallerViewInterface? = null;

    init {
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams = params
    }

    fun load(scriptContent: String)
    {
        var native: Native = Native(context, this);
        var script = DefinePathFromMainViewScriptHack.getPath(scriptContent);

        // register services
        native.addService("NativeHttp", NativeHttp(native));
        native.addService("NativeStore", NativeStore());
        native.addService("NativeHost", NativeHost(native));

        // register native code
        native._javascriptEngine.set("console", ConsoleInterface::class.java, Console());
        native._javascriptEngine.set("Native", NativeInterface::class.java, native);

        // load/inject script runtme
        native._javascriptEngine.evaluate(EmbeddedScripts.Require);
        native._javascriptEngine.evaluate(EmbeddedScripts.json2);
        native._javascriptEngine.evaluate(EmbeddedScripts.Baller);
        native._javascriptEngine.evaluate("Baller.getNative = function() {return Native;}");

        // load specific view script
        native._javascriptEngine.evaluate(scriptContent);

        // start it up!
        native._javascriptEngine.evaluate("Baller.init('" + script + "', '" + native._nativeId + "')");
    }

    fun loadUrl(url: String) {
        val httpAsync = url.httpGet();

        var request = httpAsync.responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.get()
                    load(data);
                }
            }
        }
        request.join()
    }
}
