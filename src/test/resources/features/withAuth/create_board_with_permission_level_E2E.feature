Feature: Create a board with given permission level

  Kate the owner should be able to create a new board with given permission level (private / workspace visible / public).
  Based on permission (access) level users like:
  - Tom (board member),
  - Lucy (workspace member)
  - John (public user)
  can or can not read and edit board (e.g. add a new list on board)

  Background:
    Given Kate is authenticated to Trello

  @cleanup @with_workspace
  Scenario Outline: Kate the owner can create "<permission_level>" board
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "<permission_level>" board "MY BOARD"
    Then Kate sees "<permission_level>" board "MY BOARD" in workspace "WORKSPACE 1"

    Examples:
      | permission_level  |
      | private           |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario Outline: Tom the board member can read and edit "<permission_level>" board
    Given Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "<permission_level>" board "MY BOARD"
    And Kate adds Tom as "admin" to board "MY BOARD"
    Then Tom reads board "MY BOARD"
    And Tom adds list "NEW LIST 1" on "MY BOARD"

    Examples:
      | permission_level  |
      | private           |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario Outline: Lucy the workspace member can read and edit "<permission_level>" board
    Given Kate adds Lucy as "normal" to workspace "WORKSPACE 1"
    And Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "<permission_level>" board "MY BOARD"
    Then Lucy reads board "MY BOARD"
    And Lucy adds list "NEW LIST 2" on "MY BOARD"

    Examples:
      | permission_level  |
      | workspace visible |
      | public            |

  @cleanup @with_workspace
  Scenario: Lucy the workspace member can not read private board
    Given Kate adds Lucy as "normal" to workspace "WORKSPACE 1"
    And Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "private" board "MY BOARD"
    Then Lucy can not read board "MY BOARD"

  @cleanup @with_workspace
  Scenario Outline: John the public user can not read "<permission_level>" board
    Given Kate adds Lucy as "normal" to workspace "WORKSPACE 1"
    And Kate wants "<permission_level>" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "<permission_level>" board "MY BOARD"
    Then John can not read board "MY BOARD"

    Examples:
      | permission_level  |
      | private           |
      | workspace visible |

  @cleanup @with_workspace
  Scenario: John the public user can read but not edit public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "public" board "MY BOARD"
    Then John reads board "MY BOARD"
    And John can not add list "NEW LIST 3" on "MY BOARD"
