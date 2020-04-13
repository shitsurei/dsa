package LeetCode;

import java.util.*;

/**
 * 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。
 * 你的设计需要支持以下的几个功能：
 * postTweet(userId, tweetId): 创建一条新的推文
 * getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
 * follow(followerId, followeeId): 关注一个用户
 * unfollow(followerId, followeeId): 取消关注一个用户
 * <p>
 * 示例:
 * Twitter twitter = new Twitter();
 * // 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
 * twitter.postTweet(1, 5);
 * // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
 * twitter.getNewsFeed(1);
 * // 用户1关注了用户2.
 * twitter.follow(1, 2);
 * // 用户2发送了一个新推文 (推文id = 6).
 * twitter.postTweet(2, 6);
 * // 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
 * // 推文id6应当在推文id5之前，因为它是在5之后发送的.
 * twitter.getNewsFeed(1);
 * // 用户1取消关注了用户2.
 * twitter.unfollow(1, 2);
 * // 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
 * // 因为用户1已经不再关注用户2.
 * twitter.getNewsFeed(1);
 */
public class Twitter {
    /**
     * 思路：利用哈希表加优先级队列
     * 1 每个用户将自己发送的推文存入各自的优先级队列中，每次从队列中取出的都是最新发送的推文（通过自增ID标示）
     * 2 每个用户维护自己关注的其他用户集合（用例中可能产生重复，可以用set去重）
     * 3 每次获取最新10条信息时，遍历用户自己和其关注的其他用户的优先级队列，每次取出所有队列中最新发送的推文（比较堆顶元素的ID大小）
     * 直到选出10条推文或所有队列中为空时结束，将这些取出的推文按顺序返回其推文id，再将这些推文放回各自的优先级队列
     * 4 关注和取关用户时只需要维护各自的关注集合即可
     */
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 1);
        System.out.println(twitter.getNewsFeed(1));
        twitter.follow(2, 1);
        System.out.println(twitter.getNewsFeed(2));
        twitter.unfollow(2, 1);
        System.out.println(twitter.getNewsFeed(2));
    }

    private class Tweet {
        private int tweetId;
        private int userId;
        private int order;

        public Tweet(int tweetId, int userId, int order) {
            this.tweetId = tweetId;
            this.userId = userId;
            this.order = order;
        }
    }

    private Map<Integer, PriorityQueue<Tweet>> tweets;
    private Map<Integer, HashSet<Integer>> follows;
    private int order;

    public Twitter() {
        tweets = new HashMap<>();
        follows = new HashMap<>();
        order = 0;
    }

    public void postTweet(int userId, int tweetId) {
        PriorityQueue<Tweet> heap = tweets.getOrDefault(userId, new PriorityQueue<>((a, b) -> b.order - a.order));
        tweets.put(userId, heap);
        heap.add(new Tweet(tweetId, userId, order++));
    }

    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> follow = follows.getOrDefault(userId, new HashSet<>());
        follow.add(userId);
        List<Tweet> poll = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            boolean empty = true;
            int maxOrder = -1;
            PriorityQueue<Tweet> cur = null;
            for (int e : follow) {
                PriorityQueue<Tweet> queue = tweets.getOrDefault(e, new PriorityQueue<>((a, b) -> b.order - a.order));
                tweets.put(e, queue);
                if (!queue.isEmpty() && queue.peek().order > maxOrder) {
                    cur = queue;
                    maxOrder = cur.peek().order;
                    empty = false;
                }
            }
            if (empty)
                break;
            else
                poll.add(cur.poll());
        }
        LinkedList<Integer> ans = new LinkedList<>();
        for (int i = 0; i < poll.size(); i++)
            ans.addLast(poll.get(i).tweetId);
        for (Tweet e : poll) {
            PriorityQueue<Tweet> heap = tweets.get(e.userId);
            heap.add(e);
        }
        return ans;
    }

    public void follow(int followerId, int followeeId) {
        HashSet<Integer> follow = follows.getOrDefault(followerId, new HashSet<>());
        follow.add(followeeId);
        follows.put(followerId, follow);
    }

    public void unfollow(int followerId, int followeeId) {
        HashSet<Integer> follow = follows.getOrDefault(followerId, new HashSet<>());
        follow.remove(followeeId);
        follows.put(followerId, follow);
    }
}
