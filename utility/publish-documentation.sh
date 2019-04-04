if [ "$TRAVIS_REPO_SLUG" == "fragaLY/booking-system" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo "Publishing javadoc..."

  cp -R build/docs/javadoc $HOME/javadoc-latest

	echo "Cloning the repo to $HOME directory"
  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=master https://${GITHUB_TOKEN}@github.com/fragaLY/booking-system master > /dev/null

	echo "Switching to master /dev/null"
  cd master

  echo "Removing indexes"
  git rm -rf ./javadoc

  echo "Copying latest java docs to ./docs/ folder"
  cp -Rf $HOME/javadoc-latest .
  git add -f .
  git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to master"

	if ! git push -fq origin gh-pages &> /dev/null; then
     echo "Error pushing master to origin."
  else
     echo "Published Javadoc to master."
  fi

fi