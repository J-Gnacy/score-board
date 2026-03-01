There is intentional duplication of name validation tests across Match, ScoreBoard, and TeamNameValidator. 
This is a conscious decision — every public class and method is bound by its own contract that should be tested independently.

It is assumed that updating a match score does not treat the match as new, and therefore does not affect its position in the summary ordering.
