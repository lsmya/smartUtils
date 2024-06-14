package cn.lsmya.smart.base
/*
 Copyright © 2015, 2016 Jenly Yu <a href="mailto:jenly1314@gmail.com">Jenly</a>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import cn.lsmya.smart.R
import cn.lsmya.smart.vb.BaseVBActivity

/**
 * @author [Jenly](mailto:jenly1314@gmail.com)
 */
abstract class SplashActivity<VB:ViewBinding> : BaseVBActivity<VB>() {

    open fun getAnimationListener(): Animation.AnimationListener? {
        return null
    }

    private fun startAnimation(rootView: View) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_alpha)
        anim.setAnimationListener(getAnimationListener())
        rootView.startAnimation(anim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAnimation(getContentView(this))
    }

    private fun getContentView(activity: Activity): View {
        val view = activity.window.decorView as ViewGroup
        val content = view.findViewById<FrameLayout>(android.R.id.content)
        return content.getChildAt(0)
    }
}