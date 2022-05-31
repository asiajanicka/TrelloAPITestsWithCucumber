Feature: Create a board with given commenting on cards preferences

  Kate the owner should be able to create a new board with given permission (access) level with restricted access for
  adding comments on cards.
  Based on:
  - permission level (private / workspace visible / public)
  - user group who is allowed to comment on card (only board members / board and workspace members / all users (public))

  users like Tom (board member), Lucy (workspace member) and John (public user) can or can not add comments
  to cards on board

  Background:
    Given "Kate" is authenticated to Trello

  @cleanup @with_workspace
  Scenario Outline: Kate the owner can create a <permission_level> board where <comment_group> can add comments on cards
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<comment_group>" can add comments on cards
    When Kate creates board "MY BOARD" with commenting access set to "<comment_group>"
    Then Kate sees board "MY BOARD" in workspace "WORKSPACE 1" with "<comment_group>" allowed for commenting

    Examples:
      | permission_level  | comment_group       |
      | private           | only board members |
      | workspace visible | only board members |
      | workspace visible | workspace members  |
      | public            | only board members |
      | public            | workspace members  |
      | public            | public users       |

  @cleanup @with_workspace
  Scenario: Kate the owner can not create private board where "workspace members" can add comments on cards
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    And where "workspace members" can add comments on cards
    When Kate tries to create board
    Then response is "board is private" with status code 400

  @cleanup @with_workspace
  Scenario Outline: Kate the owner can not create <permission_level> board where "public users" can add comments on cards
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "public users" can add comments on cards
    When Kate tries to create board
    Then response is "board is not public" with status code 400

    Examples:
      | permission_level  |
      | private           |
      | workspace visible |

  @cleanup @with_workspace
  Scenario Outline: Tom the board member can comment on cards in <permission_level> board (where "<commenting_group>" are allowed for commenting)
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<comment_group>" can add comments on cards
    When Kate creates board "MY BOARD" with commenting access set to "<comment_group>"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Tom" adds comment "I'll do it" on card "design new UI"

    Examples:
      | permission_level  | comment_group       |
      | private           | only board members |
      | workspace visible | only board members |
      | workspace visible | workspace members  |
      | public            | only board members |
      | public            | workspace members  |
      | public            | public users       |

  @cleanup @with_workspace
  Scenario: Tom the board member can not comment on cards in private board with "disabled" commenting
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    And where commenting on cards is disabled
    When Kate creates board "MY BOARD" with commenting access set to "disabled"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Tom" can not add comment "I'll do it" on card "design new UI"

  @cleanup @with_workspace
  Scenario Outline: Lucy the workspace member can comment on cards in <permission_level> board (where "<comment_group>" are allowed for commenting)
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<comment_group>" can add comments on cards
    When Kate creates board "MY BOARD" with commenting access set to "<comment_group>"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Lucy" adds comment "I'll do it" on card "design new UI"

    Examples:
      | permission_level  | comment_group      |
      | workspace visible | workspace members |
      | public            | workspace members |
      | public            | public users      |

  @cleanup @with_workspace
  Scenario Outline: Lucy the workspace member can not comment on cards in <permission_level> board (where "only board members" are allowed for commenting)
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "only board members" can add comments on cards
    When Kate creates board "MY BOARD" with commenting access set to "only board members"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Lucy" can not add comment "I'll do it" on card "design new UI"

    Examples:
      | permission_level  |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario: Lucy the workspace member can not comment on cards in workspace visible board with "disabled" commenting
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE 1"
    And where commenting on cards is disabled
    When Kate creates board "MY BOARD" with commenting access set to "disabled"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Lucy" can not add comment "I'll do it" on card "design new UI"

  @cleanup @with_workspace
  Scenario: John the public user can comment on cards in public board (where "public users" are allowed for commenting)
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where "public users" can add comments on cards
    When Kate creates board "MY BOARD" with commenting access set to "public users"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "John" adds comment "I'll do it" on card "design new UI"

  @cleanup @with_workspace
  Scenario Outline: John the public user can not comment on cards in public board (where "<comment_group>" are allowed for commenting)
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where "<comment_group>" can add comments on cards
    When Kate creates board "MY BOARD" with commenting access set to "<comment_group>"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "John" can not add comment "I'll do it" on card "design new UI"

    Examples:
      | comment_group       |
      | only board members |
      | workspace members  |

  @cleanup @with_workspace
  Scenario: John the public user can not comment on cards in public board with "disabled" commenting
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where commenting on cards is disabled
    When Kate creates board "MY BOARD" with commenting access set to "disabled"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "John" can not add comment "I'll do it" on card "design new UI"