package com.avic.leetcode

import android.content.Context
import android.util.Log

/**
 * 不考虑七对的情况
 * 1.手牌必须只能有两种花色,多余两种提示打缺,手牌数量对3取余,必须余1,否则提示方牌了
 * 2.如果一个花色刚好是三的倍数,那可胡牌型就一定是另一个花色
 * 2.仅有的两种花色中分别从 1 - 9 ,依次去尝试是否可以胡牌,设本次尝试的可胡的牌设为X ,并将X插入到原有牌型中
 * 3.将新的牌型中取出一对将牌,里面可能有多种情况,每一种情况都要全部尝试,将取出的将牌剩下的牌进行下一步检测
 * 4.剩下的牌我们从小到大开始检测,查询是否满足123 , 111,满足就移除,继续执行第四步,直到牌变成没有牌,则代表插入的该X,取的出该对将牌可以胡,就可以不用执行下一对将牌检测了
 */

val WanCard = mapOf(
    1 to "一万",
    2 to "二万",
    3 to "三万",
    4 to "四万",
    5 to "伍万",
    6 to "六万",
    7 to "七万",
    8 to "八万",
    9 to "九万"
)


val TongCard = mapOf(
    11 to "一筒",
    12 to "二筒",
    13 to "三筒",
    14 to "四筒",
    15 to "伍筒",
    16 to "六筒",
    17 to "七筒",
    18 to "八筒",
    19 to "九筒"
)

val TiaoCard = mapOf(
    21 to "一条",
    22 to "二条",
    23 to "三条",
    24 to "四条",
    25 to "伍条",
    26 to "六条",
    27 to "七条",
    28 to "八条",
    29 to "九条"
)

class Ma(private val context: Context) {

    val result = mutableListOf<String>()

    /**
     * 计算听牌,返回值代表是否
     */
    fun calcTing(
        listWan: MutableList<Int>,
        listTong: MutableList<Int>,
        listTiao: MutableList<Int>
    ): String {
        result.clear()
        if (listWan.isEmpty() && listTiao.isEmpty() && listTong.isEmpty()) {
            return "先输入你手中的牌型"
        }
        if (listWan.isNotEmpty() && listTiao.isNotEmpty() && listTong.isNotEmpty()) {
            return "请先打缺"
        }
        val size = listWan.size + listTiao.size + listTong.size
        if (size % 3 != 1 || size > 13) {
            return "你方牌拉,等着赔叫吧"
        }
        val allCard = mutableListOf<Int>()
        allCard.addAll(listWan)
        allCard.addAll(listTiao)
        allCard.addAll(listTong)
        //从万字开始拆将牌
        if (listWan.size % 3 == 0) {
            Log.d("lkw-majiang", "没有可以胡的万字")
        } else {
            isCanHu(allCard, "万")
        }
        //从条字开始拆将牌
        if (listTiao.size % 3 == 0) {
            Log.d("lkw-majiang", "没有可以胡的条字")
        } else {
            isCanHu(allCard, "条")
        }
        //从筒字开始拆将牌
        if (listTong.size % 3 == 0) {
            Log.d("lkw-majiang", "没有可以胡的筒字")
        } else {
            isCanHu(allCard, "筒")
        }
        val stringBuffer = StringBuffer()
        result.distinct()
        result.forEach {
            stringBuffer.append("$it ")
        }
        return stringBuffer.toString()
    }

    /**
     * 检测某种花色是否可胡
     */
    private fun isCanHu(allCard: MutableList<Int>, type: String) {
        val originCard = when (type) {
            "万" -> WanCard
            "筒" -> TongCard
            else -> TiaoCard
        }

        /**
         * 该循环是对所有牌依次去尝试 是否可以胡
         */
        originCard.keys.forEach { x ->
            val cards = allCard.toMutableList()
            cards.add(x) //将X插入到牌型中 组成 3n+ 2 的牌型
            if (cards.size == 2) {
                if (cards[0] == cards[1]) {
                    Log.d("lkw-majiang", "[${originCard[x]}]可以胡")
                    result.add(originCard.getValue(x))
                    return
                } else {
                    return
                }
            }

            //取出一对将牌,从14张牌中取出一对相同的值,可能有多种情况
            cards.sort()
            for (i in 0 until cards.size) {
                //jiangs中的数据 均等于本次循环的值(当前参与的是)
                val jiangs = cards.filter {
                    it == cards[i]
                }
                if (jiangs.size > 1) {
                    Log.d("lkw-majiang", "[${cards[i]}]可以为将牌")
                    val noJiangCards = cards.toMutableList()
                    //移除两张将牌,现在还有12张牌
                    noJiangCards.remove(jiangs[0])
                    noJiangCards.remove(jiangs[1])
                    val canhu = keZiOrShunZi(noJiangCards)
                    if (canhu) {
                        Log.d("lkw-majiang", "[${originCard[x]}]可以胡")
                        result.add(originCard.getValue(x))
                        return@forEach
                    }
                } else {
                    Log.d("lkw-majiang", "[${cards[i]}]不可以为将牌")
                }

            }

        }

    }


    /**
     * 是否包含顺子 或者 克子
     */
    private fun keZiOrShunZi(noJiang: MutableList<Int>): Boolean {
        if (noJiang.size == 0) {
            return true
        }
        val filter = noJiang.filter {
            it == noJiang[0]
        }
        /**
         * 组成了克子,移除掉
         */
        if (filter.size > 2) {
            noJiang.remove(filter[0])
            noJiang.remove(filter[0])
            noJiang.remove(filter[0])
            return keZiOrShunZi(noJiang)
        } else {
            val contaisShunzi =
                noJiang.contains(noJiang[0] + 1) && noJiang.contains(noJiang[0] + 2)
            if (contaisShunzi) {
                noJiang.remove(noJiang[0] + 2)
                noJiang.remove(noJiang[0] + 1)
                noJiang.remove(noJiang[0] + 0)
                return keZiOrShunZi(noJiang)
            }
            return false
        }
    }
}