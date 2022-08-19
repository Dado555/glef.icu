<template>
  <div class="container mx-auto  border-b border-gray-600 px-4 py-4">
    <form>
      <AwesomeVueStarRating
          :star="this.star"
          :disabled="!isUser()"
          :maxstars="this.maxStars"
          :starsize="this.starSize"
          :hasresults="this.hasResults"
          :hasdescription="this.hasDescription"
          :ratingdescription="this.ratingDescription"
      />
      <div class="form__group">
        <label>Leave a comment</label>
        <textarea
            v-model="newComment"
            rows="10"
            required
            cols="50"
            placeholder="type in your comment"
        />
        <button @click="addComment()" class="rounded bg-yellow-500 text-black cursor-auto" v-if="mode === 'new'">Submit</button>
        <button @click="editComment()" class="rounded bg-yellow-500 text-black cursor-auto" v-if="mode === 'edit'">Edit</button>
      </div>
    </form>
  </div>
</template>

<script>
import AwesomeVueStarRating from "awesome-vue-star-rating";
import {authService} from "@/services/authService";
import {commentService} from "@/services/commentService";

export default {
  name: "AddComment",
  props: {
    mode: {
      required: true,
    },
    commentDb: {
      required: false
    },
  },
  data() {
    return {
      newComment: '',
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
    };
  },
  components: {
    AwesomeVueStarRating
  },
  methods: {
    isUser() {
      return authService.isUser()
    },
    addComment() {
      let userId = parseInt(this.$store.state.user.id);
      let movieId = this.$route.params.id;
      let comment = {
        movie_id: movieId,
        user_id: userId,
        text: this.newComment,
        like_stars: parseInt(this.star)
      };
      commentService.addComment(comment).then((response) => {
        console.log(response.data);
      })
    },
    editComment() {
      let commentUpdate = {
        id: parseInt(this.commentDb.id),
        text: this.newComment,
        like_stars: parseInt(this.star),
        reports_number: parseInt(this.commentDb.reports_number)
      };
      commentService.updateComment(commentUpdate).then((response) => {
        console.log(response.data);
      });
    }
  },
  mounted() {
    if(this.mode === "edit") {
      this.star = this.commentDb.like_stars;
      this.newComment = this.commentDb.text;
    }
  }
};
</script>

<style scoped>
label {
  display: block;
  margin-bottom: 1em;
  font-weight: 700;
  font-family: Padauk, sans-serif;
}

textarea {
  width: 100%;
  margin-top: 0.5em;
  color: black;
}

button {
  border: unset;
  /*background: #79b791;*/
  /*background: yellow;*/
  color: #230c0f;
  font-weight: 700;
  padding: 1em 2.5em;
  margin-top: 1em;
}
</style>