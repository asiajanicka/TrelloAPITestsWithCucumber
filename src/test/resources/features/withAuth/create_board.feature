Feature: Create a board

  Kate should be able to create a new board with chosen parameters in the given workspace.

  Background: when Kate is authenticated
    Given "Kate" is authenticated to Trello

#  name, workspace id, default params
  @cleanUp
  Scenario: Kate can create board in given workspace with default params
    Given board "MY BOARD" and workspace "WORKSPACE 1"
    When Kate creates board "MY BOARD" with default params
    Then Kate sees board "MY BOARD" in workspace "WORKSPACE 1" with default params

#  name: name of max length / name of min length
  @cleanup
  Scenario Outline: Kate can create board with name of "<length_value>" length
    Given board name of "<length_value>" length and workspace "WORKSPACE 1"
    When Kate creates board with name of limit length
    Then Kate sees board with name of limit length in workspace "WORKSPACE 1"

    Examples:
      | length_value |
      | min          |
      | max          |

#   name: with special characters
  @cleanup
  Scenario Outline: Kate can create board with special name "<board_name>"
    Given board "<board_name>" and workspace "WORKSPACE 1"
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
      | "!"          |
      | "@"          |
      | "#"          |
      | "$"          |
      | "%"          |
      | "^"          |
      | "&"          |
      | "*"          |
      | "("          |
      | ")"          |
      | "-"          |
      | "_"          |
      | "+"          |
      | "="          |
      | "\\\"        |
      | ":"          |
      | ";"          |
      | "\\""        |
      | "'"          |
      | "<"          |
      | ","          |
      | ">"          |
      | "."          |
      | "?"          |
      | "/"          |

#  name: blank name
  @cleanUp
  Scenario: Kate can not create board with blank name
    Given board "" and workspace "WORKSPACE 1"
    When Kate tries to create board
    Then response is "invalid value for name" with status code 400

#  name: name over max length
  @cleanup
  Scenario: Kate can not create board with name over max length
    Given board name of "over max" length and workspace "WORKSPACE 1"
    When Kate tries to create board
    Then response is "Your browser sent an invalid request" with status code 400