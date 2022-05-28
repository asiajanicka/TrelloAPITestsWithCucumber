Feature: Create a board with given permission level

  Kate the owner should be able to create a new board with given permission level

  Background:
    Given "Kate" is authenticated to Trello
    And Kate creates workspace "WORKSPACE PL"

#prefs_permissionLevel: permission set to board members
  @cleanup
  Scenario: Kate the owner can create private board
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "private" board "MY BOARD"
    Then Kate sees "private" board "MY BOARD" in workspace "WORKSPACE PL"

#prefs_permissionLevel: permission set to board members
  @cleanup
  Scenario: Tom the board member can read and edit private board
    Given Kate wants "private" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "private" board "MY BOARD"
    And Kate adds Tom as "admin" to board "MY BOARD"
    And "Tom" reads board "MY BOARD"
    And "Tom" adds list "NEW LIST 1" on "MY BOARD"

#  prefs_permissionLevel: permission set to board members
  @cleanup
  Scenario: Lucy the workspace member can not read private board
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE PL"
    And Kate wants "private" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "private" board "MY BOARD"
    And "Lucy" can not read board "MY BOARD"

    #  prefs_permissionLevel: permission set to board members
  @cleanup
  Scenario: John the public user can not read private board
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE PL"
    And Kate wants "private" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "private" board "MY BOARD"
    And "John" can not read board "MY BOARD"

# prefs_permissionLevel: permission set to workspace members
  @cleanup
  Scenario: Kate the owner create board visible to workspace members
    Given Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "workspace visible" board "MY BOARD"
    Then Kate sees "workspace" board "MY BOARD" in workspace "WORKSPACE PL"

#  prefs_permissionLevel: permission set to workspace members
  @cleanup
  Scenario: Tom the board member can read and edit board visible to workspace members
    Given Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "workspace visible" board "MY BOARD"
    And Kate adds Tom as "admin" to board "MY BOARD"
    And "Tom" reads board "MY BOARD"
    And "Tom" adds list "NEW LIST 1" on "MY BOARD"

#  prefs_permissionLevel: permission set to workspace members
  @cleanup
  Scenario: Lucy the workspace member can read and edit board visible to workspace members
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE PL"
    And Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "workspace visible" board "MY BOARD"
    And "Lucy" reads board "MY BOARD"
    And "Lucy" adds list "NEW LIST 2" on "MY BOARD"

#  prefs_permissionLevel: permission set to workspace members
  @cleanup
  Scenario: John the public user can not read board visible to workspace members
    Given Kate wants "workspace visible" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "workspace visible" board "MY BOARD"
    And "John" can not read board "MY BOARD"

# prefs_permissionLevel: permission set to public users
  @cleanup
  Scenario: Kate the owner can create public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "public" board "MY BOARD"
    Then Kate sees "public" board "MY BOARD" in workspace "WORKSPACE PL"

# prefs_permissionLevel: permission set to public users
  @cleanup
  Scenario: Tom the board member can read and edit public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "public" board "MY BOARD"
    Then Kate adds Tom as "admin" to board "MY BOARD"
    And "Tom" reads board "MY BOARD"
    And "Tom" adds list "NEW LIST 1" on "MY BOARD"

# prefs_permissionLevel: permission set to public users
  @cleanup
  Scenario: Lucy the workspace member can read and edit public board
    Given Kate adds "Lucy" as "normal" to workspace "WORKSPACE PL"
    And Kate wants "public" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "public" board "MY BOARD"
    And "Lucy" reads board "MY BOARD"
    And "Lucy" adds list "NEW LIST 2" on "MY BOARD"

# prefs_permissionLevel: permission set to public users
  @cleanup
  Scenario: John the public user read but not edit public board
    Given Kate wants "public" board "MY BOARD" in "WORKSPACE PL"
    When Kate creates "public" board "MY BOARD"
    And "John" reads board "MY BOARD"
    And "John" can not add list "NEW LIST 3" on "MY BOARD"
