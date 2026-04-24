class CommentNode {
    constructor(comment_id, user_id, content, timestamp, likes) {
        this.comment_id = comment_id;
        this.user_id = user_id;
        this.content = content.substring(0, 100);
        this.timestamp = timestamp;
        this.likes = likes;
        this.replies = [];
    }

    addReply(reply) {
        this.replies.push(reply);
    }
}

function display_thread(comment, level) {
    let indent = "";
    for (let i = 0; i < level; i++) {
        indent = indent + "  ";
    }

    
    console.log(indent + "Comment " + comment.comment_id + " (" + comment.user_id + "): " + comment.content);
   

    console.log(indent + "Comment " + comment.comment_id + " (" + comment.user_id + "): " + comment.content);
  
    for (const reply of comment.replies) {
        display_thread(reply, level + 1);
    }
}

function count_total_comments(comment) {
    let count = 1;
    
    for (const reply of comment.replies) {
        count = count + count_total_comments(reply);
    }
 

    for (const reply of comment.replies) {
        count = count + count_total_comments(reply);
    }

    return count;
}

function total_likes(comment) {
    let sum = comment.likes;
    
    for (const reply of comment.replies) {
        sum = sum + total_likes(reply);
    }
   
    for (const reply of comment.replies) {
        sum = sum + total_likes(reply);
    }

    return sum;
}

function find_deepest_reply(comment) {
    if (comment.replies.length === 0) {
        return 0;
    }
  
    let max_depth = 0;
    for (const reply of comment.replies) {
        let depth = find_deepest_reply(reply);
        if (depth > max_depth) {
            max_depth = depth;
        }
    }

    return max_depth + 1;
}

function search_by_user(user_id, comment) {
    let result = [];
    
    if (comment.user_id === user_id) {
        result.push(comment);
    }
    

    if (comment.user_id === user_id) {
        result.push(comment);
    }

    for (const reply of comment.replies) {
        let found = search_by_user(user_id, reply);
        for (const item of found) {
            result.push(item);
        }
    }

    return result;
}

function contains_keyword(keyword, comment) {
    if (comment.content.toLowerCase().includes(keyword.toLowerCase())) {
        return true;
    }

    for (const reply of comment.replies) {
        if (contains_keyword(keyword, reply)) {
            return true;
        }
    }

    return false;
}

function delete_comment(comment_id, thread) {
    if (thread.comment_id === comment_id) {
        return null;
    }

    let new_replies = [];
    for (const reply of thread.replies) {
        let result = delete_comment(comment_id, reply);
        if (result !== null) {
            new_replies.push(result);
        }
    }

    thread.replies = new_replies;
    return thread;
}

module.exports = {
    CommentNode,
    display_thread,
    count_total_comments,
    total_likes,
    find_deepest_reply,
    search_by_user,
    contains_keyword,
    delete_comment
};
