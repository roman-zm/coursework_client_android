package com.namor.coursework.fragment

import com.arellomobile.mvp.MvpAppCompatFragment
import com.namor.coursework.MainListener

abstract class CourseworkFragment: MvpAppCompatFragment() {
    lateinit var listener: MainListener
}