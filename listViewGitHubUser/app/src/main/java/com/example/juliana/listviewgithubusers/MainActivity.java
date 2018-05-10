package com.example.juliana.listviewgithubusers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juliana.listviewgithubusers.Clases.GitHubUser;
import com.example.juliana.listviewgithubusers.Clases.Group;
import com.example.juliana.listviewgithubusers.Clases.User;
import com.example.juliana.listviewgithubusers.Conexiones.Conexion;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<Group> groupList = new ArrayList<>();
    private ExpandableListView exp_list;
    private Conexion conexion;
    private ArrayList<User> listaUsers = new ArrayList<>();
    ArrayList<GitHubUser> expandableChilds = new ArrayList<>();
    private Group groupListView;

    private int contPag = 1;
    //private int pageCount = 0;
    private ExpandListAdapter ExpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exp_list = (ExpandableListView) findViewById(R.id.expandableListView);
        obtenerUsuarios(Integer.toString(contPag));

        /*

        load more information

        exp_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {


            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, final int totalItems) {
                final int lastItem = firstItem + visibleItemCount;
                if (lastItem == totalItems) {
                    System.out.println("essta actualizando mas datos");
                    contPag = contPag + 1 ;
                    System.out.println("Integer.toString(contPag): " + Integer.toString(contPag));
                   // obtenerUsuarios(Integer.toString(contPag));
                }
            }
        });
        */
    }

    public void obtenerUsuarios(String pagina){
        conexion = Conexion.getInstance();
        try {
            Call<ArrayList<User>> call = conexion.getServidor().getAction("1");
            call.enqueue(new Callback<ArrayList<User>>() {
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    listaUsers = response.body(); //lista de usuarios (api solo muestra username)
                    //se requieren datos entendibles para el usuario. Se obtiene por medio de otra api (datos del usuario dueño del username)
                    if(listaUsers == null){
                        Toast.makeText(getApplicationContext(), "Error conexion " , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        obtener(); //se obtiene
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error conexion " , Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error conexion " , Toast.LENGTH_SHORT).show();
        }
    }


    public void obtener(){ //obtiene la informacion extra
        for (int i = 0; i< listaUsers.size(); i++){
            final String nombre = listaUsers.get(i).getLogin();

            conexion = Conexion.getInstance();
            try {
                Call<GitHubUser> call = conexion.getServidor().servidorObtenerInfoGitHubUser(listaUsers.get(i).getLogin());
                final int finalI = i;
                call.enqueue(new Callback<GitHubUser>() {
                    @Override
                    public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {


                        expandableChilds = new ArrayList<>();
                        expandableChilds.add(response.body());

                        groupListView = new Group(nombre,expandableChilds);
                        groupList.add(groupListView);

                        if(finalI == listaUsers.size()-1 ){ //cuando se obtiene la informacion extra para cada usuario se carga el listview
                            llenarExpandableList();
                        }
                    }
                    @Override
                    public void onFailure(Call<GitHubUser> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error conexion " , Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void llenarExpandableList(){
        ExpAdapter = new ExpandListAdapter(MainActivity.this, groupList);
        exp_list.setAdapter(ExpAdapter);
    }

    public class ExpandListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private ArrayList<Group> groups;

        public ExpandListAdapter(Context context, ArrayList<Group> groups) {
            this.context = context;
            this.groups = groups;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            ArrayList<GitHubUser> chList = groups.get(groupPosition)
                    .getGitHubUser();
            return chList.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            GitHubUser child = (GitHubUser) getChild(groupPosition,
                    childPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.row, null);
            }

            //agregando valores
            TextView nombre = (TextView) convertView.findViewById(R.id.rowTexViewValorNombre);
            if(child.getName()== null){
                nombre.setText("Sin nombre");
            }
            else{
                nombre.setText(child.getName().toString());
            }


            TextView cantRepot = (TextView) convertView.findViewById(R.id.rowTexViewValorCantRepos);
            cantRepot.setText(Integer.toString(child.getPublic_repos()));

            TextView follower = (TextView) convertView.findViewById(R.id.rowTexViewValorFollower);
            follower.setText(Integer.toString(child.getFollowers()));


            //cambiando el titulo de los listview child

            TextView nombreTitle = (TextView) convertView.findViewById(R.id.rowTexViewTitleFollower);
            nombreTitle.setText("Cantidad Seguidores");

            TextView cantRepotTitle = (TextView) convertView.findViewById(R.id.rowTexViewTitleCantRepos);
            cantRepotTitle.setText("Cant Repositorios");

            TextView followerTitle = (TextView) convertView.findViewById(R.id.rowTexViewTitleNombre);
            followerTitle.setText("Nombre");

            return convertView;

        }
        @Override
        public int getChildrenCount(int groupPosition) {
            ArrayList<GitHubUser> chList = groups.get(groupPosition)
                    .getGitHubUser();

            return chList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            Group group = (Group) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.list_group, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.lblListHeader);
            tv.setText(group.getName());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
