Feature: Create a board with given permission level

  Kate the owner should be able to create a new board with given permission level (private / workspace visible / public)

  Background:
    Given "Kate" is authenticated to Trello

#prefs_permissionLevel: permission set to board members
  @cleanup @with_workspace
  Scenario: Kate the owner can create private board
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "private" board "MY BOARD"
    Then Kate sees "private" board "MY BOARD" in workspace "WORKSPACE 1"

#prefs_permissionLevel: permission set to board members
  @cleanup @with_workspace
  Scenario: Tom the board member can read and edit private board
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "private" board "MY BOARD"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    Then "Tom" reads board "MY BOARD"
    And "Tom" adds list "NEW LIST 1" on "MY BOARD"

#  prefs_permissionLevel: permission set to board members
  @cleanup @with_workspace
  Scenario: Lucy the workspace member can not read private board
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "private" board "MY BOARD"
    Then "Lucy" can not read board "MY BOARD"

#    prefs_permissionLevel: permission set to board members
  @cleanup @with_workspace
  Scenario: John the public user can not read private board
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "private" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "private" board "MY BOARD"
    Then "John" can not read board "MY BOARD"

# prefs_permissionLevel: permission set to workspace members
  @cleanup @with_workspace
  Scenario: Kate the owner create board visible to workspace members
    Given Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "workspace visible" board "MY BOARD"
    Then Kate sees "workspace visible" board "MY BOARD" in workspace "WORKSPACE 1"

#  prefs_permissionLevel: permission set to workspace members
  @cleanup @with_workspace
  Scenario: Tom the board member can read and edit board visible to workspace members
    Given Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "workspace visible" board "MY BOARD"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    Then "Tom" reads board "MY BOARD"
    And "Tom" adds list "NEW LIST 1" on "MY BOARD"

#  prefs_permissionLevel: permission set to workspace members
  @cleanup @with_workspace
  Scenario: Lucy the workspace member can read and edit board visible to workspace members
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "workspace visible" board "MY BOARD"
    Then "Lucy" reads board "MY BOARD"
    And "Lucy" adds list "NEW LIST 2" on "MY BOARD"

#  prefs_permissionLevel: permission set to workspace members
  @cleanup @with_workspace
  Scenario: John the public user can not read board visible to workspace members
    Given Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "workspace visible" board "MY BOARD"
    Then "John" can not read board "MY BOARD"

# prefs_permissionLevel: permission set to public users
  @cleanup @with_workspace
  Scenario: Kate the owner can create public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "public" board "MY BOARD"
    Then Kate sees "public" board "MY BOARD" in workspace "WORKSPACE 1"

# prefs_permissionLevel: permission set to public users
  @cleanup @with_workspace
  Scenario: Tom the board member can read and edit public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "public" board "MY BOARD"
    And Kate adds "Tom" as "admin" to board "MY BOARD"
    Then "Tom" reads board "MY BOARD"
    And "Tom" adds list "NEW LIST 1" on "MY BOARD"

# prefs_permissionLevel: permission set to public users
  @cleanup @with_workspace
  Scenario: Lucy the workspace member can read and edit public board
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE 1"
    And Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "public" board "MY BOARD"
    Then "Lucy" reads board "MY BOARD"
    And "Lucy" adds list "NEW LIST 2" on "MY BOARD"

# prefs_permissionLevel: permission set to public users
  @cleanup @with_workspace
  Scenario: John the public user read but not edit public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE 1"
    When Kate creates "public" board "MY BOARD"
    Then "John" reads board "MY BOARD"
    And "John" can not add list "NEW LIST 3" on "MY BOARD"
