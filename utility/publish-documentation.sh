if [ "$TRAVIS_REPO_SLUG" == "fragaLY/booking-system" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo "Publishing javadoc..."

  cp -R build/docs/javadoc $HOME/javadoc-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GITHUB_TOKEN}@github.com/fragaLY/booking-system gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./javadoc
  cp -Rf $HOME/javadoc-latest ./javadoc
  git add -f .
  git commit -m "Latest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to master"

	if ! git push -fq origin gh-pages &> /dev/null; then
     echo "Error pushing gh-pages to origin."
  else
     echo "Published Javadoc to gh-pages."
  fi

fi