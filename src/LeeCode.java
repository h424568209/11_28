import java.util.*;

public class LeeCode {
    /**
     * 滑动窗口的最大值
     * @param nums 数组
     * @param k 滑动窗口的大小
     * @return 每个窗口的最大值
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if(n*k == 0 )
            return new int[]{0};
        if(k==1){
            return nums;
        }
        //left[j]是从块的开始到下标j最大的元素 方向左-》右
        //right[j] 是从块的结尾到下标 j 最大的元素，方向 右->左
        //两数组一起可以提供两个块内元素的全部信息。考虑从下标 i 到下标 j的滑动窗口。
        // right[i] 是左侧块内的最大元素，
        // left[j] 是右侧块内的最大元素。
        // 因此滑动窗口中的最大元素为 max(right[i], left[j])。

        int []left = new int[n];
        left[0] = nums[0];
        int []right = new int[n];
        right[n-1] = nums[n-1];
        for(int i= 1 ; i < n ; i++){
            //从左向右
            //每个大小为K的窗口的开始，直接是这个值
            if(i%k == 0 ){
                left[i] = nums[i];
                // 如果不是开始 则是这个窗口到现在的最大的值
            }else{
                left[i] = Math.max(left[i-1],nums[i]);
            }
            int j = n-i-1;
            //从右向左
            if((j+1)%k == 0 )
                right[j] = nums[j];
            else
                right[j] = Math.max(right[j+1],nums[j]);
        }
        int []output = new int[n-k+1];
        for(int i = 0 ;i< n-k+1 ; i++){
            output[i] = Math.max(left[i+k-1],right[i]);
        }
        return output;

        //O(Nk)的时间复杂度  N为元素的个数
        //O（N-K+1）的空间复杂度  用于输出数组
   /*
        if( k == 0 || nums.length == 0){
            return new int[]{0};
        }
        int []output = new int[nums.length-k+1];
        for(int i = 0 ; i < nums.length-k+1 ; i++){
            int max =Integer.MIN_VALUE;
            for(int j = i; j < i+k ; j++){
                max = Math.max(max,nums[j]);
            }
            output[i] = max;
        }
        return output;
        */
    }
    /**
     * 无重复字符的最长子字符串
     * @param s 字符串
     * @return 最长的子字符串的长度
     */
    public int lengthOfLongestSubstring(String s) {
        int r = 0 , l  = 0 , maxLen = 0 ;
        Set<Character> set = new HashSet<>();
        while(r<s.length() && l < s.length()){
            if(!set.contains(s.charAt(r))){
                set.add(s.charAt(r++));
                maxLen = Math.max(maxLen,r-l);
            }else{
                set.remove(s.charAt(l++));
            }
        }
        return maxLen;
    }
    /**
     *  最小覆盖子串
     * @param s 字符串
     * @param t 字符串
     * @return S中包含T的最小子串
     */
    public String minWindow(String s, String t) {
        if(s == null || t ==null || s.length() < t.length() )
            return "";
        int left = 0 , right = 0 ;
        int len = Integer.MAX_VALUE;
        String res = "";
        int []source = new int[256];
        int []target = new int[256];
        for(char c:t.toCharArray())
            target[c]++;
        while(right<s.length()){
            if(!valid(source,target)){
                source[s.charAt(right++)]++;
            }
            while(valid(source,target)){
                if(right - left < len){
                    len = Math.min(len,right-left);
                    res = s.substring(left,right);
                }
                source[s.charAt(left++)]--;
            }
        }
        return res;
    }

    private boolean valid(int[] source, int[] target) {
        for(int i =0 ; i < source.length ; i++){
            //说明source中不包含target
            if(source[i] < target[i]){
                return false;
            }
        }
        return true;
    }

    /**
     *  替换后的最长的重复字符
     * @param s 字符串
     * @param k 可替换个数
     * @return 最长的字符串的长度
     *
     * duct字典用来记录每一个字符出现的次数（在window内）
     * l r表示window的左右边界 r<len(s) maxLen记录出现了最多的次数的字符
     * 每次更新maxLen，当（r-l+1）- maxLen  也就是window的长度 减去 出现了最多次数的字符
     * 与K相比，如果大于k说明window已经不满足要求了，需要从左边缩window
     * 更新res，max（res，len（window））
     * r++不断扩大window
     */
    public int characterReplacement(String s, int k) {
        if(s==null)
            return 0;
        int l = 0, r = 0, res = 0 ,maxLen = 0;
        int[]dict = new int[256];
        while(r<s.length()){
            //记录窗口中的字符的多少
            dict[s.charAt(r)]++;
            //更新窗口中最大的字符的大小
            maxLen = Math.max(maxLen,dict[s.charAt(r)]);
            // 当窗口的大小 减去 最大的相同字符的长度 大于K的时候。即无法再进行修改元素了
            //说明需要更新窗口
            while((r-l+1 - maxLen)>k){
                dict[s.charAt(l++)]--;
            }
            //最终的结果中保存窗口的大小和之前结果的最大值
            res = Math.max(res,r-l+1);
            //不断的更新右节点进行更新窗口
            r++;
        }
        return res;
    }
    /**
     * 用栈在遍历给定字符串的过程中去判断到目前为止扫描的子字符串的有效性
     * 同时能的都最长有效的字符串长度
     */
    public int longestValidParenthesesWithStack(String s) {
        Stack<Integer> stack =new Stack<>();
        stack.push(-1);
        int maxLen = 0;
        for(int i =0 ; i < s.length() ; i++){
            if(s.charAt(i) == '('){
                stack.push(i);
            }else{
                stack.pop();
                if(stack.isEmpty()){
                   stack.push(i);
                }else{
                    maxLen = Math.max(maxLen,i-stack.peek());
                }
            }
        }
        return maxLen;
    }
    /**
     *  考虑给定字符串中每种可能的非空偶数长度子字符串，检查它是否是一个有效括号字符串序列
     *  栈中放偶数个字符，检查它是不是有效的括号，将长度放在maxlen中
     * @param s 字符串
     * @return 字符串中最长的有效括号的长度
     */
    public int longestValidParentheses(String s) {
        int maxlen = 0;
        for(int i =0 ; i < s.length() ; i++){
            for(int j = i+2 ; j<= s.length() ; j+=2){
                //如果字符串有效更新则更新maxLen的大小
                if(isValid(s.substring(i,j)))
                    maxlen = Math.max(maxlen,j-i);
            }
        }
        return maxlen;
    }
    //判断s字符串是否是有效的括号匹配
    public boolean isValid(String s){
        Stack<Character> stack = new Stack<>();

        for(int i =0 ; i < s.length() ; i++){
            char c =s.charAt(i);
            if(c == '('){
                stack.push(c);
            }else if(!stack.isEmpty() && stack.peek() == '('){
                stack.pop();
            }else{
                return false;
            }
        }
        return stack.isEmpty();
    }
    /**
     * 找出所有相加之和为 n 的 k 个数的组合。
     * 组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
     * 使用回溯算法进行求解
     */
    List<List<Integer>> ans  = new ArrayList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        traceBack(k,n,0,1,new LinkedList<>());
        return   ans;
    }

    private void traceBack(int k, int n, int sum, int begin, LinkedList<Integer> list) {
        if(k == 0 ){
            if(n == sum)
                ans.add(new ArrayList<>(list));
                return;
        }
        for(int i = begin ; i< 10 ; i++){
            list.add(i);
            traceBack(k-1,n,sum+i,i+1,list);
            list.removeLast();
        }
    }

    public static void main(String[] args) {
        LeeCode l = new LeeCode();
        System.out.println(l.longestValidParentheses("((()("));
        System.out.println(l.characterReplacement("ABAB",2));
        System.out.println(l.lengthOfLongestSubstring("ABCABCD"));
    }
}
