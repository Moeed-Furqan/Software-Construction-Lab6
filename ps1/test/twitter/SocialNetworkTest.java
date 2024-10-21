/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment
     * looks like.
     * Make sure you have partitions.
     */

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNoMentions() {
        Instant timestamp = Instant.now(); // Current time for testing
        List<Tweet> tweets = List.of(new Tweet(1, "user", "Hello world!", timestamp));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("expected empty graph", followsGraph.get("user").isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSingleMention() {
        Instant timestamp = Instant.now();
        List<Tweet> tweets = List.of(new Tweet(1, "ernie", "Hello @bert!", timestamp));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.containsKey("ernie"));
        assertFalse(followsGraph.get("ernie").contains("bert"));
    }

    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        Instant timestamp = Instant.now();
        List<Tweet> tweets = List.of(
                new Tweet(1, "ernie", "Hello @bert @bigbird!", timestamp));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue(followsGraph.containsKey("ernie"));
        assertFalse(followsGraph.get("ernie").containsAll(Set.of("bert", "bigbird")));
    }

    @Test
    public void testInfluencersSingleUserNoFollowers() {
        Map<String, Set<String>> followsGraph = Map.of("ernie", Set.of());
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersSingleInfluencer() {
        Map<String, Set<String>> followsGraph = Map.of(
                "ernie", Set.of("bert"));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertEquals(List.of("bert"), influencers);
    }

    @Test
    public void testInfluencersMultipleInfluencers() {
        Map<String, Set<String>> followsGraph = Map.of(
                "ernie", Set.of("bert"),
                "bigbird", Set.of("bert", "elmo"));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertEquals(List.of("bert", "elmo"), influencers);
    }

    @Test
    public void testInfluencersTieBetweenUsers() {
        Map<String, Set<String>> followsGraph = Map.of(
                "ernie", Set.of("bert"),
                "bigbird", Set.of("elmo"));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected bert or elmo first",
                influencers.equals(List.of("bert", "elmo")) ||
                        influencers.equals(List.of("elmo", "bert")));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
