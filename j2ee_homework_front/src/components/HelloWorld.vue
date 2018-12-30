<template>
  <div id="app">
    <label>在线总人数：{{totalNumber}}，已登录用户数：{{loggedInNumber}}，游客数：{{visitorNumber}}</label>
    <form method="get" action="logout">
      <table border="0" align="left">
        <tr>
          <td>Hello</td>
          <td>{{userId}}</td>
          <td><input type="submit" value="Log out"></td>
        </tr>
      </table>
    </form>
    <br>
    <br>
    <div style="float: left;">
      <form method="post" action="add-to-cart">
        <table border="1" align=left style="margin-right: 50px; margin-bottom: 30px">
          <tr>
            <td colspan="4" align="center">List of Commodities</td>
          </tr>
          <tr>
            <td>name</td>
            <td>price (￥)</td>
            <td>description</td>
            <td>number to buy</td>
          </tr>
          $commodities
          <tr>
            <td colspan="4" align="center">
              <input type="submit" value="Add to cart">
              <input type="reset" value="Reset">
            </td>
          </tr>
        </table>
      </form>
      <form method="post" action="pay">
        <table border="1" align=left>
          <tr>
            <td colspan="4" align="center">Cart</td>
          </tr>
          <tr>
            <td>name</td>
            <td>unit price (￥)</td>
            <td>number</td>
            <td>total (￥)</td>
          </tr>
          $cart
          <tr>
            <td colspan="4" align="right">Total price: ￥{{totalPrice}}</td>
          </tr>
          <tr>
            <td colspan="4" align="center">
              <input type="submit" value="Pay">
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
</template>

<script>
module.exports = {
  data: function () {
    return {
      userId: '123',
      totalNumber: 1,
      loggedInNumber: 0,
      visitorNumber: 1,
      totalPrice: 100
    }
  }
}
</script>

<style scoped>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #66ccff;
    margin-top: 60px;
  }
</style>
