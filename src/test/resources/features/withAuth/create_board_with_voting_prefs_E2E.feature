Feature: Create a board with given voting access

  Kate the owner should be able to create a new board with given permission (access) level with restricted access for voting on cards.
  Based on:
  - permission level (private / workspace visible / public)
  - voting group preference (only board members / board and workspace members / all users (public))

  users like Tom (board member), Lucy (workspace member) and John (public user) can or can not vote on cards on board

  Background:
    Given "Kate" is authenticated to Trello

  @cleanup @with_workspace
  Scenario Outline: Kate the owner can create a <permission_level> board where <voting_group> can vote on cards
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<voting_group>" can vote on cards
    When Kate creates board "MY BOARD" with voting set to "<voting_group>"
    Then Kate sees board "MY BOARD" in workspace "WORKSPACE 1" with voting set to "<voting_group>"

    Examples:
      | permission_level  | voting_group       |
      | private           | only board members |
      | workspace visible | only board members |
      | workspace visible | workspace members  |
      | public            | only board members |
      | public            | workspace members  |
      | public            | public users       |

  @cleanup @with_workspace
  Scenario: Kate the owner can not create private board with "workspace members" voting
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    And where "workspace members" can vote on cards
    When Kate tries to create board
    Then response is "board is private" with status code 400

  @cleanup @with_workspace
  Scenario Outline: Kate the owner can not create <permission_level> board with "public users" voting
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "public users" can vote on cards
    When Kate tries to create board
    Then response is "board is not public" with status code 400

    Examples:
      | permission_level  |
      | private           |
      | workspace visible |

  @cleanup @with_workspace
  Scenario Outline: Tom the board member can vote on cards in <permission_level> board with "<voting_group>" voting
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<voting_group>" can vote on cards
    When Kate creates board "MY BOARD" with voting set to "<voting_group>"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Tom" votes on card "design new UI"

    Examples:
      | permission_level  | voting_group       |
      | private           | only board members |
      | workspace visible | only board members |
      | workspace visible | workspace members  |
      | public            | only board members |
      | public            | workspace members  |
      | public            | public users       |

  @cleanup @with_workspace
  Scenario: Tom the board member can vote on cards in private board with "disabled" voting
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    And where voting on cards is disabled
    When Kate creates board "MY BOARD" with voting set to "disabled"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Tom" can not vote on card "design new UI"

  @cleanup @with_workspace
  Scenario Outline: Lucy the workspace member can vote on cards in <permission_level> board with "<voting_group>" voting
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<voting_group>" can vote on cards
    When Kate creates board "MY BOARD" with voting set to "<voting_group>"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Lucy" votes on card "design new UI"

    Examples:
      | permission_level  | voting_group      |
      | workspace visible | workspace members |
      | public            | workspace members |
      | public            | public users      |

  @cleanup @with_workspace
  Scenario Outline: Lucy the workspace member can not vote on cards in <permission_level> board with "only board members" voting
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "only board members" can vote on cards
    When Kate creates board "MY BOARD" with voting set to "only board members"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Lucy" can not vote on card "design new UI"

    Examples:
      | permission_level  |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario: Lucy the workspace member can not vote on cards in workspace visible board with "disabled" voting
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE 1"
    And where voting on cards is disabled
    When Kate creates board "MY BOARD" with voting set to "disabled"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "Lucy" can not vote on card "design new UI"

  @cleanup @with_workspace
  Scenario: John the public user can vote on cards in public board with "public users" voting
   Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where "public users" can vote on cards
    When Kate creates board "MY BOARD" with voting set to "public users"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "John" votes on card "design new UI"

  @cleanup @with_workspace
  Scenario Outline: John the public user can not vote on cards in public board with "<voting_group>" voting
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where "<voting_group>" can vote on cards
    When Kate creates board "MY BOARD" with voting set to "<voting_group>"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "John" can not vote on card "design new UI"

    Examples:
      | voting_group       |
      | only board members |
      | workspace members  |

  @cleanup @with_workspace
  Scenario: John the public user can not vote on cards in public board with "disabled" voting
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where voting on cards is disabled
    When Kate creates board "MY BOARD" with voting set to "disabled"
    And Kate sees default list "To Do" on board "MY BOARD"
    And Kate adds card "design new UI" to list "To Do"
    Then "John" can not vote on card "design new UI"

