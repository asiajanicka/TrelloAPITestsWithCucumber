Feature: Create a board with basic params

  Kate the owner should be able to create a new board with chosen parameters in the given workspace.

  Background: when Kate is authenticated
    Given "Kate" is authenticated to Trello

#  name, workspace id, default params
  @cleanup @with_workspace
  Scenario: Kate can create board in given workspace with default params
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    When Kate creates board "MY BOARD" with default params
    Then Kate sees board "MY BOARD" in workspace "WORKSPACE 1" with default params

#  name: name of max length / name of min length
  @cleanup @with_workspace
  Scenario Outline: Kate can create board with name of <length_value> length
    Given board name of "<length_value>" length and workspace "WORKSPACE 1"
    When Kate creates board with name of "<length_value>" length
    Then Kate sees board with name of limit length in workspace "WORKSPACE 1"

    Examples:
      | length_value |
      | min          |
      | max          |

#   name: with special characters
  @cleanup @with_workspace
  Scenario Outline: Kate can create board with special name <board_name>
    Given board name "<board_name>" and workspace "WORKSPACE 1"
    When Kate creates board "<board_name>"
    Then Kate sees board "<board_name>" in workspace "WORKSPACE 1"

    Examples:
      | board_name   |
      | BOARDNAME    |
      | BOARD_NAME   |
      | BOARD NAME   |
      | boardName    |
      | <b>board</b> |
      | 1            |
      | !            |
      | @            |
      | #            |
      | $            |
      | %            |
      | ^            |
      | &            |
      | *            |
      | (            |
      | )            |
      | -            |
      | _            |
      | +            |
      | =            |
      | \\\          |
      | :            |
      | ;            |
      | \\"          |
      | '            |
      | <            |
      | ,            |
      | >            |
      | .            |
      | ?            |
      | /            |

#  name: blank name
  @cleanup @with_workspace
  Scenario: Kate can not create board with blank name
    Given board name "" and workspace "WORKSPACE 1"
    When Kate tries to create board
    Then response is "invalid value for name" with status code 400

#  name: name over max length
  @cleanup @with_workspace
  Scenario: Kate can not create board with name over max length
    Given board name of "over max" length and workspace "WORKSPACE 1"
    When Kate tries to create board
    Then response is "Your browser sent an invalid request" with status code 400

#  no name param in request
  @cleanup @with_workspace
  Scenario: Kate can not create board if there is no name param in request
    Given workspace "WORKSPACE 1"
    When Kate tries to create board
    Then response is "invalid value for name" with status code 400

#  name of already existing board in workspace
  @cleanup @with_workspace
  Scenario: Kate can create board with name of already existing board in given workspace
    Given Kate already created board "MY BOARD" in workspace "WORKSPACE 1"
    And board name "MY BOARD" and workspace "WORKSPACE 1"
    When Kate creates board "MY BOARD"
    Then Kate sees two boards with name "MY BOARD" in workspace "WORKSPACE 1"

#  no defaultLabels
  @cleanup @with_workspace
  Scenario: Kate can create board without default labels
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And Kate wants board without "defaultLabels"
    When Kate creates board "MY BOARD"
    Then Kate sees board "MY BOARD" without default labels in workspace "WORKSPACE 1"

#  defaultLabels: invalid value for defaultLabels / blank defaultLabels
  @cleanup @with_workspace
  Scenario Outline: Kate can not create board if default labels param is set to <value> value
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And Kate wants board with "defaultLabels" set to "<value>" value
    When Kate tries to create board
    Then response is "invalid value for defaultLabels" with status code 400

    Examples:
      | value   |
      | blank   |
      | invalid |

# no defaultLists
  @cleanup @with_workspace
  Scenario: Kate can create board without default lists
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And Kate wants board without "defaultLists"
    When Kate creates board "MY BOARD"
    Then Kate sees board "MY BOARD" without default lists in workspace "WORKSPACE 1"

# defaultLabels: invalid value for defaultLists / blank defaultLists
  @cleanup @with_workspace
  Scenario Outline: Kate can not create board if default lists param is set to <value> value
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And Kate wants board with "defaultLists" set to "<value>" value
    When Kate tries to create board
    Then response is "invalid value for defaultLists" with status code 400

    Examples:
      | value   |
      | blank   |
      | invalid |

# desc: description of max/middle/min length
  @cleanup @with_workspace
  Scenario Outline: Kate can create board with description of <length> length
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And Kate wants board with description of "<length>" length
    When Kate creates board "MY BOARD"
    Then Kate sees board "MY BOARD" with correct description in workspace "WORKSPACE 1"

    Examples:
      | length |
      | max    |
      | middle |
      | min    |

# desc: description over max length
  @cleanup @with_workspace
  Scenario: Kate can not create board with description over max length
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And Kate wants board with description of "over max" length
    When Kate tries to create board
    Then response is "Your browser sent an invalid request." with status code 400

# idOrganization: invalid workspace id
  @cleanup
  Scenario: Kate can not create board in workspace with invalid id
    Given board name "MY BOARD" and workspace with "invalid" id
    When Kate tries to create board
    Then response is "unauthorized org access" with status code 401

# idOrganization: non existing workspace
  @cleanup
  Scenario: Kate can not create board in non existing workspace
    Given board name "MY BOARD" and id of workspace that was deleted
    When Kate tries to create board
    Then response is "unauthorized org access" with status code 401

# idOrganization: blank idOrganization
  @cleanup
  Scenario: Kate can create board without giving workspace name (one workspace exists on her account)
    Given board name "MY BOARD"
    When Kate creates board "MY BOARD"
    Then Kate sees board "MY BOARD"

#  prefs_permissionLevel: blank or invalid
  @cleanup @with_workspace
  Scenario Outline: Kate can not create board with <value> permissions level prefs
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And permission level set to "<value>" value
    When Kate tries to create board
    Then response is "invalid value for prefs_permissionLevel" with status code 400

    Examples:
      | value   |
      | blank   |
      | invalid |

#  prefs_voting: blank or invalid
  @cleanup @with_workspace
  Scenario Outline: Kate can not create board with <value> value for voting group who can vote on cards
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And voting group set to "<value>" value
    When Kate tries to create board
    Then response is "invalid value for prefs_voting" with status code 400

    Examples:
      | value   |
      | blank   |
      | invalid |

#  prefs_comments: blank or invalid
  @cleanup @with_workspace
  Scenario Outline: Kate can not create board with <value> value for commenting group who can add comments on cards
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And commenting group set to "<value>" value
    When Kate tries to create board
    Then response is "invalid value for prefs_comments" with status code 400

    Examples:
      | value   |
      | blank   |
      | invalid |

#  prefs_invitations: blank or invalid
  @cleanup @with_workspace
  Scenario Outline: Kate can not create board with <value> value for user group who can invite others to board
    Given board name "MY BOARD" and workspace "WORKSPACE 1"
    And invite group set to "<value>" value
    When Kate tries to create board
    Then response is "invalid value for prefs_invitations" with status code 400

    Examples:
      | value   |
      | blank   |
      | invalid |