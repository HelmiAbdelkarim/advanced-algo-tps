const {
    CommentNode,
    display_thread,
    count_total_comments,
    total_likes,
    find_deepest_reply,
    search_by_user,
    contains_keyword,
    delete_comment
} = require('./exercice1.js');

function createSampleThread() {
    let comment101 = new CommentNode(101, "Alice", "This recipe looks amazing!", new Date("2024-01-15 10:00"), 15);
    let reply201 = new CommentNode(201, "Bob", "I tried it last night!", new Date("2024-01-15 11:30"), 8);
    let reply301 = new CommentNode(301, "Alice", "What did you think?", new Date("2024-01-15 12:00"), 3);
    let reply401 = new CommentNode(401, "Bob", "It was delicious!", new Date("2024-01-15 12:30"), 12);
    let reply202 = new CommentNode(202, "Charlie", "Can I use olive oil instead?", new Date("2024-01-15 11:45"), 5);
    let reply302 = new CommentNode(302, "Alice", "Yes, that works too!", new Date("2024-01-15 13:00"), 7);
    reply301.addReply(reply401);
    reply201.addReply(reply301);
    reply202.addReply(reply302);
    comment101.addReply(reply201);
    comment101.addReply(reply202);

    return comment101;
}

function runTests() {
    console.log("=".repeat(60));
    console.log("TEST: display_thread(comment, level)");
    console.log("=".repeat(60));
    let thread = createSampleThread();
    display_thread(thread, 0);

    console.log("\n" + "=".repeat(60));
    console.log("TEST: count_total_comments(comment)");
    console.log("=".repeat(60));
    let totalComments = count_total_comments(thread);
    console.log("Result: " + totalComments);
    console.log("Expected: 6");

    console.log("\n" + "=".repeat(60));
    console.log("TEST: total_likes(comment)");
    console.log("=".repeat(60));
    let totalLikesResult = total_likes(thread);
    console.log("Result: " + totalLikesResult);
    console.log("Expected: 50");

    console.log("\n" + "=".repeat(60));
    console.log("TEST: find_deepest_reply(comment)");
    console.log("=".repeat(60));
    let maxDepth = find_deepest_reply(thread);
    console.log("Result: " + maxDepth);
    console.log("Expected: 3");

    console.log("\n" + "=".repeat(60));
    console.log("TEST: search_by_user(user_id, comment)");
    console.log("=".repeat(60));
    let aliceComments = search_by_user("Alice", thread);
    console.log("Alice comments found: " + aliceComments.length);
    for (const c of aliceComments) {
        console.log("  - Comment " + c.comment_id + ": " + c.content);
    }
    console.log("Expected: 3 comments (101, 301, 302)");

    let bobComments = search_by_user("Bob", thread);
    console.log("\nBob comments found: " + bobComments.length);
    for (const c of bobComments) {
        console.log("  - Comment " + c.comment_id + ": " + c.content);
    }
    console.log("Expected: 2 comments (201, 401)");

    console.log("\n" + "=".repeat(60));
    console.log("TEST: contains_keyword(keyword, comment)");
    console.log("=".repeat(60));
    console.log("Contains 'delicious': " + contains_keyword("delicious", thread) + " (Expected: true)");
    console.log("Contains 'recipe': " + contains_keyword("recipe", thread) + " (Expected: true)");
    console.log("Contains 'terrible': " + contains_keyword("terrible", thread) + " (Expected: false)");
    console.log("Contains 'olive oil': " + contains_keyword("olive oil", thread) + " (Expected: true)");

    console.log("\n" + "=".repeat(60));
    console.log("TEST: delete_comment(comment_id, thread)");
    console.log("=".repeat(60));
    let threadForDeletion = createSampleThread();
    console.log("Before deletion - Total: " + count_total_comments(threadForDeletion));
    
    console.log("\nDeleting comment 201 (cascades to 301 and 401)...\n");
    let updatedThread = delete_comment(201, threadForDeletion);
   

    console.log("\nDeleting comment 201 (cascades to 301 and 401)...\n");
    let updatedThread = delete_comment(201, threadForDeletion);

    console.log("After deletion:");
    display_thread(updatedThread, 0);
    console.log("\nRemaining comments: " + count_total_comments(updatedThread));
    console.log("Expected: 3");

    console.log("\n" + "=".repeat(60));
    console.log("ALL TESTS COMPLETED");
    console.log("=".repeat(60));
}

runTests();