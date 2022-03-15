import React from "react";

import origin from "assets/origin.jpg";

interface AppProps {}
const App: React.FC<AppProps> = () => {
  return (
    <div>
      <img src={origin} alt="logo" />
    </div>
  );
};

export default App;
