<template>
  <div class="comment">
    <div class="comment__flex">
      <p style="font-size: larger; font-style: italic">Username</p>
      <br/>
      <AwesomeVueStarRating
          :star="this.star"
          :disabled="this.disabled"
          :maxstars="this.maxStars"
          :starsize="this.starSize"
          :hasresults="this.hasResults"
          :hasdescription="this.hasDescription"
          :ratingdescription="this.ratingDescription"
      />
      <p>I found this article helpful</p>
      <div class="comment__flex-btn">
        <button class="update rounded bg-yellow-500 text-black cursor-auto">Update</button>
        <button class="del rounded bg-yellow-500 text-black cursor-auto">Delete</button>
      </div>
    </div>
  </div>
</template>

<script>
import AwesomeVueStarRating from "awesome-vue-star-rating";

export default {
  name: "Comment",
  components: {
    AwesomeVueStarRating
  },
  data() {
    return {
      star: 2,
      ratingDescription: [
        {
          text: "Poor",
          class: "star-poor",
        },
        {
          text: "Below Average",
          class: "star-belowAverage",
        },
        {
          text: "Average",
          class: "star-average",
        },
        {
          text: "Good",
          class: "star-good",
        },
        {
          text: "Excellent",
          class: "star-excellent",
        },
      ],
      hasResults: false,
      hasDescription: true,
      starSize: "lg", //[xs,lg,1x,2x,3x,4x,5x,6x,7x,8x,9x,10x],
      maxStars: 5,
      disabled: false,
    }
  }
}
</script>

<style>
.message {
  margin-bottom: 2em;
}
.comment {
  margin: 1em 0 2em;
  border-bottom: 1px solid #ccc;
}
.comment__flex-btn {
  margin: 1em 0;
}
button + button {
  margin-left: 1em;
}
button {
  padding: 0.75em 1em;
  border: unset;
}
.del {
  background: #f93943;
  color: #000807;
  height: 40px;
  width: 100px;
}
.update {
  background: #7eb2dd;
  color: #000807;
  height: 40px;
  width: 100px;
}
textarea {
  width: 100%;
}
.open {
  margin: 1em 0;
}
</style>