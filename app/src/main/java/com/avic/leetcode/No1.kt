package com.avic.leetcode

import android.util.Log

/**

给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。

你可以按任意顺序返回答案。

 

示例 1：

输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
示例 2：

输入：nums = [3,2,4], target = 6
输出：[1,2]
示例 3：

输入：nums = [3,3], target = 6
输出：[0,1]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/two-sum
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class No1 {

    companion object {
        fun testSolution() {
            val nums = intArrayOf(2, 7, 11, 15)
            val target = 9
            val no1 = No1()
            val solution = no1.solution1(nums, target)
            Log.d("leetCode-No1", "${solution[0]}   ${solution[1]}")
        }

    }

    /**
     *这个题的最容易想到的就是暴力解法，
     * 两层循环，第一层中依次取出数组中的每个元素，第二层中依次取出第一层数组元素之后的元素，然后两个数相加与目标值target进行对比。代码如下
     * 空间复杂度是 O（1），时间复杂度是O（n^2）
     */
    fun solution1(nums: IntArray, target: Int): IntArray {
        nums.forEachIndexed { i, _ ->
            for (j in i + 1..nums.size) {
                return intArrayOf(nums[i], nums[j])
            }
        }
        throw Exception("无此答案")
    }

}