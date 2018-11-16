### Unload Script Error Demo (Byteman)

This project demonstrats 2 scenarios that seem to handled incorrectly by Byteman.

Consider a use-case where 2 rules (rule1 and rule2) are loaded for the same trigger point:

1. If rule1 is unloaded from Byteman during the execution of a rule1, rule2 should still be executed, but it is not.
2. If rule2 is unloaded from Byteman during the execution of a rule1, rule2 should not be executed, but it is.

To reproduce the errors: Clone and run `mvn install`