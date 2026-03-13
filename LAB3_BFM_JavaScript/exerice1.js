function createStoryNode(story_id, user_id, content_preview, timestamp) {
    return {
      story_id: story_id,
      user_id: user_id,
      content_preview: content_preview,
      timestamp: timestamp,
      views: 0,
      next: null,
      prev: null
    };
  }
  
  function createDoublyLinkedList() {
    return {
      head: null,
      tail: null,
      current: null,
      size: 0
    };
  }
  
  function add_story(L, node) {
    if (L.head === null) {
      L.head = node;
      L.tail = node;
      L.current = node;
      node.next = null;
      node.prev = null;
    } else {
      node.prev = L.tail;
      node.next = null;
      L.tail.next = node;
      L.tail = node;
    }
  
    L.size = L.size + 1;
  }
  
  function remove_story(L, story_id) {
    let temp = L.head;
  
    while (temp !== null && temp.story_id !== story_id) {
      temp = temp.next;
    }
  
    if (temp === null) {
      return false;
    }
  
    if (temp.prev !== null) {
      temp.prev.next = temp.next;
    } else {
      L.head = temp.next;
    }
  
    if (temp.next !== null) {
      temp.next.prev = temp.prev;
    } else {
      L.tail = temp.prev;
    }
  
    if (L.current === temp) {
      if (temp.next !== null) {
        L.current = temp.next;
      } else {
        L.current = temp.prev;
      }
    }
  
    if (L.head !== null) {
      L.head.prev = null;
    }
  
    if (L.tail !== null) {
      L.tail.next = null;
    }
  
    L.size = L.size - 1;
  
    if (L.size === 0) {
      L.head = null;
      L.tail = null;
      L.current = null;
    }
  
    return true;
  }
  
  function move_forward(L) {
    if (L.current === null) {
      return null;
    }
  
    if (L.current.next !== null) {
      L.current = L.current.next;
    }
  
    return L.current;
  }
  
  function move_backward(L) {
    if (L.current === null) {
      return null;
    }
  
    if (L.current.prev !== null) {
      L.current = L.current.prev;
    }
  
    return L.current;
  }
  
  function jump_to(L, story_id) {
    let temp = L.head;
  
    while (temp !== null) {
      if (temp.story_id === story_id) {
        L.current = temp;
        return temp;
      }
      temp = temp.next;
    }
  
    return null;
  }
  
  function insert_after(L, current_id, new_story) {
    let temp = L.head;
  
    while (temp !== null && temp.story_id !== current_id) {
      temp = temp.next;
    }
  
    if (temp === null) {
      return false;
    }
  
    new_story.prev = temp;
    new_story.next = temp.next;
  
    if (temp.next !== null) {
      temp.next.prev = new_story;
    } else {
      L.tail = new_story;
    }
  
    temp.next = new_story;
    L.size = L.size + 1;
  
    return true;
  }
  
  function display_around_current(L, k) {
    if (L.current === null) {
      console.log("Feed is empty");
      return;
    }
  
    let temp = L.current;
    let count = 0;
  
    while (temp.prev !== null && count < k) {
      temp = temp.prev;
      count = count + 1;
    }
  
    count = 0;
  
    while (temp !== null && count < 2 * k + 1) {
      console.log(
        "story_id:", temp.story_id,
        "| preview:", temp.content_preview,
        "| views:", temp.views
      );
      temp = temp.next;
      count = count + 1;
    }
  }
  
  function track_view(L) {
    if (L.current !== null) {
      L.current.views = L.current.views + 1;
    }
  }
  
  function most_viewed(L) {
    if (L.head === null) {
      return null;
    }
  
    let best = L.head;
    let temp = L.head.next;
  
    while (temp !== null) {
      if (temp.views > best.views) {
        best = temp;
      }
      temp = temp.next;
    }
  
    return best;
  }
  
  function reorder_by_views(L) {
    if (L.head === null || L.head.next === null) {
      return;
    }
  
    let sorted_head = null;
    let temp = L.head;
  
    while (temp !== null) {
      let next_node = temp.next;
  
      temp.prev = null;
      temp.next = null;
  
      if (sorted_head === null) {
        sorted_head = temp;
      } else if (temp.views > sorted_head.views) {
        temp.next = sorted_head;
        sorted_head.prev = temp;
        sorted_head = temp;
      } else {
        let current = sorted_head;
  
        while (current.next !== null && current.next.views >= temp.views) {
          current = current.next;
        }
  
        temp.next = current.next;
  
        if (current.next !== null) {
          current.next.prev = temp;
        }
  
        temp.prev = current;
        current.next = temp;
      }
  
      temp = next_node;
    }
  
    L.head = sorted_head;
    L.tail = L.head;
  
    while (L.tail.next !== null) {
      L.tail = L.tail.next;
    }
  
    L.current = L.head;
  }
  
  function display_feed(L) {
    let temp = L.head;
  
    while (temp !== null) {
      console.log(
        "story_id:", temp.story_id,
        "| user_id:", temp.user_id,
        "| preview:", temp.content_preview,
        "| timestamp:", temp.timestamp,
        "| views:", temp.views
      );
      temp = temp.next;
    }
  }

module.exports = {
  createStoryNode,
  createDoublyLinkedList,
  add_story,
  remove_story,
  move_forward,
  move_backward,
  jump_to,
  insert_after,
  display_around_current,
  track_view,
  most_viewed,
  reorder_by_views,
  display_feed,
};