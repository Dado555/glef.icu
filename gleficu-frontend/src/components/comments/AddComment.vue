<template>
  <div class="container mx-auto  border-b border-gray-600 px-4 py-4">
    <form>
      <div class="form__group">
        <label>Leave a comment</label>
        <textarea
            v-model="newComment"
            rows="10"
            required
            cols="50"
            placeholder="type in your comment"
        />
        <button class="rounded bg-yellow-500 text-black cursor-auto">Submit</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "AddComment",
  data() {
    return {
      newComment: '',
    };
  },
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