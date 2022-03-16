export UNSIGNED_PSBT="cHNidP8BAH0BAAAAAbbQJrWkxcj8pWp0F3qP6Lj8t0EaEY9vNX1udLTsFfeRAQAAAAD/////AljAAAAAAAAAIgAgIzwXsSAYw9YSXI0Boaea5/VAHNUa7pgMeI8j2NvYOzYQJwAAAAAAABYAFP+dpWfmLzDqhlT6HV+9R774474TAAAAAAABAOoCAAAAAAEBPG58L5QOGL2MiSe/ecJKK8mlHKd8cWkg7kgI+BhVdvYAAAAAAP7///8CZOlxbwAAAAAWABQQ9HldjadrlH6a3J5cp+huiWy86WDqAAAAAAAAIgAgs1mEr7Q2erJBnDclpHX6Y3jaGcSCWthL9LRbleAvCQMCRzBEAiAxa00Wp1vMkTMT2EAVp6psNkiDS2SydW0TYkrQnmW3SAIgJt66RuIF7F/JJgKimdcGjQwKPYcEt6iUASwn7b4xuSgBIQLhro/Ri8dKqz8wjqnyPrYVf/5D8EwONV7BcO62hu/oaGtqIQABAStg6gAAAAAAACIAILNZhK+0NnqyQZw3JaR1+mN42hnEglrYS/S0W5XgLwkDAQVpUiEDRqnIeENADPPCm8lEcajjnuUFqSpn85pu6jWwNGZkuakhAxsI96ptt7LnkSApemwutQ2Q6TuRZKkeatveXdS+QgouIQMoBCWAUhMqouOpgZLeZfpkg8HLsijfYfnrJ0U0YcsKbVOuIgYDGwj3qm23sueRICl6bC61DZDpO5FkqR5q295d1L5CCi4YjpimCVQAAIABAACAAAAAgAAAAAAAAAAAIgYDKAQlgFITKqLjqYGS3mX6ZIPBy7Io32H56ydFNGHLCm0Y4KmcnlQAAIABAACAAAAAgAAAAAAAAAAAIgYDRqnIeENADPPCm8lEcajjnuUFqSpn85pu6jWwNGZkuakYJirLW1QAAIABAACAAAAAgAAAAAAAAAAAACICAgA7Li7UIM42OL1D5mGH9dZ39XNX9Z2AKtVy1AIvqGJ1GOCpnJ5UAACAAQAAgAAAAIAAAAAAAQAAACICA3Js0o0Y6z09C6x5fDxT3xNsrFm/jBqZ363b65RKU5p4GI6YpglUAACAAQAAgAAAAIAAAAAAAQAAACICA5ugmZ5vhDg2vLpJpo4tFs9YglsBhb/wmoFO61yfuY80GCYqy1tUAACAAQAAgAAAAIAAAAAAAQAAAAAA"

# ALICE SIGNS
export ALICE_SIGNED_PSBT=$(bdk-cli wallet -w alice -d $ALICE_SIGNING_DESCRIPTOR sign --psbt $UNSIGNED_PSBT | jq -r ".psbt")

# BOB SIGNS
export ALICE_BOB_SIGNED_PSBT=$(bdk-cli wallet -w bob -d $BOB_SIGNING_DESCRIPTOR sign --psbt $ALICE_SIGNED_PSBT | jq -r ".psbt")