module.exports = {
  "parser": "babel-eslint",
  "extends": "airbnb",
  "plugins": [
    "react",
    "import",
    "es6-recommended"
  ],
  "globals": {
    "document": true,
    "window": true
  },
  rules: {
    "import/no-extraneous-dependencies": ["error", {"devDependencies": ["**/browser/**/*", "**/*.spec.js"]}],
    "react/jsx-filename-extension": [1, {"extensions": [".js", ".jsx"]}],
    "react/prop-types": [1],
    "react/jsx-closing-tag-location": [0],
    "jsx-a11y/anchor-is-valid": [0],
    "import/prefer-default-export": [0]

  }
};