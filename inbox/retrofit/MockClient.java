public class MockClient implements Client {

    @Override
    public Response execute(Request request) throws IOException {
        Uri uri = Uri.parse(request.getUrl());

        Log.d("MOCK SERVER", "fetching uri: " + uri.toString());

        String responseString = "";

        if(uri.getPath().equals("/path/of/interest")) {
            responseString = "JSON STRING HERE";
        } else {
            responseString = "OTHER JSON RESPONSE STRING";
        }

        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", responseString.getBytes()));
    }
}

RestAdapter.Builder builder = new RestAdapter.Builder();
builder.setClient(new MockClient());


// http://mdswanson.com/blog/2013/12/16/reliable-android-http-testing-with-retrofit-and-mockito.html
Mockito.verify(mockApi).repositories(Mockito.anyString(), cb.capture());

List<Repository> testRepos = new ArrayList<Repository>();
testRepos.add(new Repository("rails", "ruby", new Owner("dhh")));
testRepos.add(new Repository("android", "java", new Owner("google")));

cb.getValue().success(testRepos, null);

assertThat(activity.getListAdapter()).hasCount(2);
