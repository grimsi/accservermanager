export const environment = {
  production: true,
  version: require('../../package.json').version,
  api_url: `/v${require('../../package.json').apiVersion}`
};
