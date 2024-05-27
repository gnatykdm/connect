#!/bin/bash

# Step 1: Checkout the master branch
git checkout master

# Step 2: Start an interactive rebase for the last 4 commits
# This will open the default editor for editing
git rebase -i HEAD~4

# Pause script and give instructions to the user
echo "Please edit the list of commits in the editor that has been opened."
echo "Remove the line corresponding to commit 760c29936cf307823c0f49f6b8f5c66d4a8faceb and save the file."
echo "Press [Enter] when you have saved and closed the editor."
read

# Step 3: Force push the changes to the remote master branch
git push origin master --force

# Confirmation message
echo "The commit 760c29936cf307823c0f49f6b8f5c66d4a8faceb has been removed and the changes have been force-pushed to the master branch."

