package com.ape.material.weather.base;

import android.content.Context;

/**
 * ************************************************************************ **
 *        _oo0oo_                               ** **                             o8888888o
 *                     ** **                             88" . "88                              **
 * **                             (| -_- |)                              ** **
 *       0\  =  /0                              ** **                           ___/'---'\___
 *                     ** **                        .' \\\|     |// '.                          **
 * **                       / \\\|||  :  |||// \\                        ** **
 * / _ ||||| -:- |||||- \\                       ** **                      | |  \\\\  -  /// |   |
 *                      ** **                      | \_|  ''\---/''  |_/ |                       **
 * **                      \  .-\__  '-'  __/-.  /                       ** **
 * ___'. .'  /--.--\  '. .'___                     ** **                 ."" '<  '.___\_<|>_/___.'
 * >'  "".                  ** **                | | : '-  \'.;'\ _ /';.'/ - ' : | |
 * ** **                \  \ '_.   \_ __\ /__ _/   .-' /  /                 ** **
 * ====='-.____'.___ \_____/___.-'____.-'=====             ** **
 * '=---='                               ** ************************************************************************
 * **                        佛祖保佑     镇类之宝                         **
 * ************************************************************************
 */
public abstract class BasePresenter<T, E> {
    protected Context mContext;
    protected E mModel;
    protected T mView;

}
