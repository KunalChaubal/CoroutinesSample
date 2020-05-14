# CoroutinesSample
This is a sample application that gives an example of implementing coroutines

This sample repository should help in dealing with some common scenarios that are encountered while implementing multiple network calls asynchronously. 
Some are pretty straightforward, while some require special handling.

These scenarios are:
1. Fire-and-forget network calls.
2. Canceling other network calls if at least one call fails.
3. Continue execution of other network calls even if anyone API call fails.
4. Canceling other network calls only if a certain error condition is encountered.
