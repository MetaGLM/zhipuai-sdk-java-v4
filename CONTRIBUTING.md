# How to contribute

We'd love to accept your patches and contributions to this project.

## Contribution process

### Code reviews

All submissions, including submissions by project members, require review. We
use GitHub pull requests for this purpose. Consult
[GitHub Help](https://help.github.com/articles/about-pull-requests/) for more
information on using pull requests.

### Contributor Guide

You may follow these steps to contribute:

1. **Fork the official repository.** This will create a copy of the official repository in your own account.
2. **Sync the branches.** This will ensure that your copy of the repository is up-to-date with the latest changes from the official repository.
3. **Work on your forked repository's feature branch.** This is where you will make your changes to the code.
4. **Commit your updates on your forked repository's feature branch.** This will save your changes to your copy of the repository.
5. **Submit a pull request to the official repository's main branch.** This will request that your changes be merged into the official repository.
6. **Resolve any linting errors.** This will ensure that your changes are formatted correctly.

Here are some additional things to keep in mind during the process:

- **Test your changes.** Before you submit a pull request, make sure that your changes work as expected.
- **Be patient.** It may take some time for your pull request to be reviewed and merged.


### Environment Setup
For running unit tests, set up your environment variables with your API credentials:

```bash
export ZHIPUAI_BASE_URL=https://open.bigmodel.cn/api/paas/v4/  # Default ZhipuAI API endpoint
export ZHIPUAI_API_KEY=your_api_key_here  # Replace with your actual API key
```

> ⚠️ **Note**: Running tests will consume a small amount of tokens from your API account.

### Dependencies

This SDK uses the following core dependencies:

| Library | Version |
|---------|----------|
| OkHttp | 3.14.9 |
| Java JWT | 4.2.2 |
| Jackson | 2.11.3 |
| Retrofit2 | 2.9.0 |


Have Fun!
---
