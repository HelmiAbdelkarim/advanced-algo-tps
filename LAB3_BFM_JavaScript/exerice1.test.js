const {
  createStoryNode,
  createDoublyLinkedList,
  add_story,
  remove_story,
  move_forward,
  track_view,
  most_viewed,
  insert_after,
  display_feed,
  reorder_by_views,
  display_around_current,
} = require("./exerice1.js");

const feed = createDoublyLinkedList();

const s1 = createStoryNode(1, 101, "Story 1", "10:00");
const s2 = createStoryNode(2, 102, "Story 2", "10:05");
const s3 = createStoryNode(3, 103, "Story 3", "10:10");

add_story(feed, s1);
add_story(feed, s2);
add_story(feed, s3);

display_feed(feed);

move_forward(feed);
track_view(feed);
track_view(feed);

move_forward(feed);
track_view(feed);

console.log("Most viewed:", most_viewed(feed));

insert_after(feed, 2, createStoryNode(4, 104, "Inserted story", "10:07"));
display_feed(feed);

remove_story(feed, 1);
display_feed(feed);

reorder_by_views(feed);
display_feed(feed);

display_around_current(feed, 1);
