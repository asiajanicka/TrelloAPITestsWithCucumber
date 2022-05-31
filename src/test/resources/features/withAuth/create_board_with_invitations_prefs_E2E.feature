Feature: Create a board with given invite to board preferences

  Kate the owner should be able to create a new board with restricted group of users who can invite other users to board.
  Groups who can invite others to board are: admins and board members.
  Based on:
  - permission level (private / workspace visible / public)
  - user group who is allowed to invite others to board

  users like Tom (board member / admin), Lucy (workspace member) and John (public user) can or can not invite others to board

  Background:
    Given "Kate" is authenticated to Trello

  @cleanup @with_workspace
  Scenario Outline: Kate the owner can create <permission_level> board where only <invite_group> can invite other users
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<invite_group>" can invite other users to board
    When Kate creates board "MY BOARD" with invite access set to "<invite_group>"
    Then Kate sees board "MY BOARD" in workspace "WORKSPACE 1" with "<invite_group>" allowed for adding users to board
    Examples:
      | permission_level  | invite_group  |
      | private           | admins        |
      | private           | board members |
      | workspace visible | admins        |
      | workspace visible | board members |
      | public            | admins        |
      | public            | board members |


  @cleanup @with_workspace
  Scenario Outline: Tom the board admin can invite other users to <permission_level> board when inviting is for <invite_group>
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<invite_group>" can invite other users to board
    When Kate creates board "MY BOARD" with invite access set to "<invite_group>"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    Then "Tom" invites "Lucy" to board "MY BOARD" via email
    Examples:
      | permission_level  | invite_group  |
      | private           | admins        |
      | private           | board members |
      | workspace visible | admins        |
      | workspace visible | board members |
      | public            | admins        |
      | public            | board members |

  @cleanup @with_workspace
  Scenario Outline: Tom the board member can invite other users to <permission_level> board when inviting is for board members
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "board members" can invite other users to board
    When Kate creates board "MY BOARD" with invite access set to "board members"
    And Kate adds "Tom" as "normal" to board "MY BOARD"
    Then "Tom" invites "Lucy" to board "MY BOARD" via email
    Examples:
      | permission_level  |
      | private           |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario Outline: Tom the board member can not invite other users to "<permission_level>" board when inviting is for admins
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "admins" can invite other users to board
    When Kate creates board "MY BOARD" with invite access set to "admins"
    And Kate adds "Tom" as "normal" to board "MY BOARD"
    Then "Tom" can not invite "Lucy" to board "MY BOARD" via email
    Examples:
      | permission_level  |
      | private           |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario Outline: Lucy the workspace member can not invite other users to "<permission_level>" board when inviting is for <invite_group>
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    And where "<invite_group>" can invite other users to board
    When Kate creates board "MY BOARD" with invite access set to "<invite_group>"
    Then "Lucy" can not invite "Tom" to board "MY BOARD" via email
    Examples:
      | permission_level  | invite_group  |
      | workspace visible | board members |
      | public            | board members |
      | workspace visible | admins        |
      | public            | admins        |

  @cleanup @with_workspace
  Scenario Outline: John the public user can not invite other users to "public" board when inviting is for <invite_group>
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    And where "<invite_group>" can invite other users to board
    When Kate creates board "MY BOARD" with invite access set to "<invite_group>"
    Then "John" can not invite "Lucy" to board "MY BOARD" via email
    Examples:
      | invite_group  |
      | admins        |
      | board members |