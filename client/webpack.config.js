require("dotenv").config();

const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

//빌드 시 CSS를 별도로 추출해 HTML DOM 추출 과정에서 JS를 불러오는 과정을 최소화 시켜주는 효과가 있다 칸다
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

//Hot reload를 사용하기 위함 
const ReactRefreshWebpackPlugin = require("@pmmmwh/react-refresh-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const CleanTerminalPlugin = require('clean-terminal-webpack-plugin');

const ProgressPlugin = require('progress-webpack-plugin');

const isProd = process.env.NODE_ENV === "production";
const PORT = process.env.PORT || 3000;

module.exports = {
  mode: isProd ? "production" : "development",
  devtool: isProd ? "hidden-source-map" : "source-map",
  entry: "./src/index.tsx",
  output: {
    filename: "static/js/[name].js",
    path: path.join(__dirname, "/build")
  },
  resolve: {
    modules: [
      path.resolve(__dirname, 'node_modules'),
      path.resolve(__dirname, 'src'),
    ],
    //import시 파일 확장명 작성 안해도 됨
    extensions: [".js", ".jsx", ".ts", ".tsx"],
    preferRelative: true
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: path.resolve(__dirname, "public", "index.html"),
      favicon: "public/icon.png",
      hash: true
    }),
    new MiniCssExtractPlugin({
      linkType: false, // 기본 값 'text/css'
      filename: "static/css/[name].css"
    }),
    new ReactRefreshWebpackPlugin(),
    new CleanWebpackPlugin(),
    new CleanTerminalPlugin({
      message: `Now Compiling...`,
      onlyInWatchMode: false
    }),
    new ProgressPlugin(true),
  ],
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: [MiniCssExtractPlugin.loader, 'css-loader']
      },
      {
        test: /\.(ts|tsx)$/,
        loader: "ts-loader",
        options: {
          transpileOnly: isProd ? false : true
        }
      },
      {
        test: /\.(webp|png|svg|jpg|jpeg|gif|ico)$/,
        loader: "file-loader",
        options: {
          name: "static/media/images/[name].[ext]?[hash]",
        }
      },
      {
        test: /.(TTF|otf|eot|svg|woff(2)?)(\?[a-z0-9]+)?$/,
        type: "javascript/auto",
        use: [{
          loader: 'file-loader',
          options: {
            name: 'static/media/fonts/[name].[ext]',
          }
        }]
      },
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: ["babel-loader", "eslint-loader"]
      }
    ]
  },
  stats: "errors-only",
  devServer: {
    static: {
      directory: path.resolve(__dirname, "public")
    },
    port: PORT,
    open: true,
    client: {
      overlay: true
    },
    hot: true,
    host: "localhost",
    historyApiFallback: true,
    compress: true
  }
};
