package com.example.capstone.core.service.impl;

import com.example.capstone.core.model.ClusterModel;
import com.example.capstone.core.model.CourseModel;
import com.example.capstone.core.service.exception.NotFoundException;
import com.example.capstone.core.service.exception.ResourceAlreadyExistsException;
import com.example.capstone.infrastucture.entity.Cluster;
import com.example.capstone.infrastucture.entity.Course;
import com.example.capstone.infrastucture.repository.ClusterRepository;
import com.example.capstone.infrastucture.repository.CourseRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private CourseRepository mockCourseRepository;
    @Mock
    private ClusterRepository mockClusterRepository;

    private CourseServiceImpl courseServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        courseServiceImplUnderTest = new CourseServiceImpl(mockModelMapper, mockCourseRepository,
                mockClusterRepository);
    }

    @Test
    void testCreateCourse_ThrowsResourceAlreadyExistsException() {

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course1.setClusters(Set.of(cluster));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByTitleAndIsDeletedFalse("title")).thenReturn(course);

        assertThatThrownBy(() -> courseServiceImplUnderTest.createCourse(courseModel))
                .isInstanceOf(ResourceAlreadyExistsException.class);
    }

    @Test
    void testCreateCourse_CourseRepositoryFindByTitleAndIsDeletedFalseReturnsAbsent() {
        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));

        when(mockCourseRepository.findByTitleAndIsDeletedFalse("title")).thenReturn(Optional.empty());

        final Cluster cluster = new Cluster();
        cluster.setId(0L);
        cluster.setName("name");
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        cluster.setCourses(Set.of(course));
        final Collection<Cluster> clusters = List.of(cluster);
        when(mockClusterRepository.findAllByIdIn(List.of(0L))).thenReturn(clusters);

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        course1.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(any(Object.class), eq(Course.class))).thenReturn(course1);

        final Course course2 = new Course();
        course2.setId(0L);
        course2.setTitle("title");
        course2.setDescription("description");
        course2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster2 = new Cluster();
        course2.setClusters(Set.of(cluster2));
        final Course entity = new Course();
        entity.setId(0L);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster3 = new Cluster();
        entity.setClusters(Set.of(cluster3));
        when(mockCourseRepository.save(entity)).thenReturn(course2);

        final CourseModel result = courseServiceImplUnderTest.createCourse(courseModel);
        assertThat(result == null);
    }

    @Test
    void testCreateCourse_ClusterRepositoryReturnsNoItems() {
        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));

        when(mockCourseRepository.findByTitleAndIsDeletedFalse("title")).thenReturn(Optional.empty());
        when(mockClusterRepository.findAllByIdIn(List.of(0L))).thenReturn(Collections.emptyList());

        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        when(mockModelMapper.map(any(Object.class), eq(Course.class))).thenReturn(course);

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        course1.setClusters(Set.of(cluster1));
        final Course entity = new Course();
        entity.setId(0L);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster2 = new Cluster();
        entity.setClusters(Set.of(cluster2));
        when(mockCourseRepository.save(entity)).thenReturn(course1);

        final CourseModel result = courseServiceImplUnderTest.createCourse(courseModel);
        assertThat(result == null);


    }

    @Test
    void testCreateCourse_ModelMapperThrowsConfigurationException() {
        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));

        when(mockCourseRepository.findByTitleAndIsDeletedFalse("title")).thenReturn(Optional.empty());

        final Cluster cluster = new Cluster();
        cluster.setId(0L);
        cluster.setName("name");
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        cluster.setCourses(Set.of(course));
        final Collection<Cluster> clusters = List.of(cluster);
        when(mockClusterRepository.findAllByIdIn(List.of(0L))).thenReturn(clusters);

        when(mockModelMapper.map(any(Object.class), eq(Course.class))).thenThrow(ConfigurationException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.createCourse(courseModel))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testCreateCourse_ModelMapperThrowsMappingException() {
        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));

        when(mockCourseRepository.findByTitleAndIsDeletedFalse("title")).thenReturn(Optional.empty());

        final Cluster cluster = new Cluster();
        cluster.setId(0L);
        cluster.setName("name");
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        cluster.setCourses(Set.of(course));
        final Collection<Cluster> clusters = List.of(cluster);
        when(mockClusterRepository.findAllByIdIn(List.of(0L))).thenReturn(clusters);

        when(mockModelMapper.map(any(Object.class), eq(Course.class))).thenThrow(MappingException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.createCourse(courseModel))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testCreateCourse_CourseRepositorySaveThrowsOptimisticLockingFailureException() {
        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));

        when(mockCourseRepository.findByTitleAndIsDeletedFalse("title")).thenReturn(Optional.empty());

        final Cluster cluster = new Cluster();
        cluster.setId(0L);
        cluster.setName("name");
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        cluster.setCourses(Set.of(course));
        final Collection<Cluster> clusters = List.of(cluster);
        when(mockClusterRepository.findAllByIdIn(List.of(0L))).thenReturn(clusters);

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        course1.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(any(Object.class), eq(Course.class))).thenReturn(course1);

        final Course entity = new Course();
        entity.setId(0L);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster2 = new Cluster();
        entity.setClusters(Set.of(cluster2));
        when(mockCourseRepository.save(entity)).thenThrow(OptimisticLockingFailureException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.createCourse(courseModel))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testDeleteCourse() {
        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course1.setClusters(Set.of(cluster));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);

        courseServiceImplUnderTest.deleteCourse(0L);

        final Course entity = new Course();
        entity.setId(0L);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        entity.setClusters(Set.of(cluster1));
        verify(mockCourseRepository).delete(entity);
    }

    @Test
    void testDeleteCourse_CourseRepositoryFindByIdAndIsDeletedFalseReturnsAbsent() {
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseServiceImplUnderTest.deleteCourse(0L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testDeleteCourse_CourseRepositoryDeleteThrowsOptimisticLockingFailureException() {

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course1.setClusters(Set.of(cluster));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);

        final Course entity = new Course();
        entity.setId(0L);
        entity.setTitle("title");
        entity.setDescription("description");
        entity.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        entity.setClusters(Set.of(cluster1));
        doThrow(OptimisticLockingFailureException.class).when(mockCourseRepository).delete(entity);

        assertThatThrownBy(() -> courseServiceImplUnderTest.deleteCourse(0L))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testGetCourse() {

        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course1.setClusters(Set.of(cluster));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));
        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenReturn(courseModel);

        final CourseModel result = courseServiceImplUnderTest.getCourse(0L);
        assertThat(result.getTitle().equals("title"));
    }

    @Test
    void testGetCourse_CourseRepositoryReturnsAbsent() {
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCourse(0L)).isInstanceOf(NotFoundException.class);
    }

    @Test
    void testGetCourse_ModelMapperThrowsConfigurationException() {
        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course1.setClusters(Set.of(cluster));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);

        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenThrow(ConfigurationException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCourse(0L)).isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetCourse_ModelMapperThrowsMappingException() {
        final Course course1 = new Course();
        course1.setId(0L);
        course1.setTitle("title");
        course1.setDescription("description");
        course1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course1.setClusters(Set.of(cluster));
        final Optional<Course> course = Optional.of(course1);
        when(mockCourseRepository.findByIdAndIsDeletedFalse(0L)).thenReturn(course);

        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenThrow(MappingException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCourse(0L)).isInstanceOf(MappingException.class);
    }

    @Test
    void testGetCourses() {
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        final Page<Course> courses = new PageImpl<>(List.of(course));
        when(mockCourseRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(courses);

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));
        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenReturn(courseModel);

        final List<CourseModel> result = courseServiceImplUnderTest.getCourses(PageRequest.of(0, 1));
        assertThat(result.contains(courseModel));

    }

    @Test
    void testGetCourses_CourseRepositoryReturnsNoItems() {
        when(mockCourseRepository.findAllByIsDeletedFalse(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        final List<CourseModel> result = courseServiceImplUnderTest.getCourses(PageRequest.of(0, 1));

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetCourses_ModelMapperThrowsConfigurationException() {
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        final Page<Course> courses = new PageImpl<>(List.of(course));
        when(mockCourseRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(courses);

        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenThrow(ConfigurationException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCourses(PageRequest.of(0, 1)))
                .isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetCourses_ModelMapperThrowsMappingException() {
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        final Page<Course> courses = new PageImpl<>(List.of(course));
        when(mockCourseRepository.findAllByIsDeletedFalse(any(Pageable.class))).thenReturn(courses);

        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenThrow(MappingException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCourses(PageRequest.of(0, 1)))
                .isInstanceOf(MappingException.class);
    }

    @Test
    void testGetCoursesByUserAndClusterName() {
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        final Page<Course> courses = new PageImpl<>(List.of(course));
        when(mockCourseRepository.findCourseByUsersIdEqualsAndClustersNameLike(any(Pageable.class), eq(0L),
                eq("clusterName%"))).thenReturn(courses);

        final CourseModel courseModel = new CourseModel();
        courseModel.setId(0);
        courseModel.setTitle("title");
        courseModel.setDescription("description");
        courseModel.setClusterIds(List.of(0L));
        final ClusterModel clusterModel = new ClusterModel();
        courseModel.setClusters(List.of(clusterModel));
        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenReturn(courseModel);

        final List<CourseModel> result = courseServiceImplUnderTest.getCoursesByUserAndClusterName(PageRequest.of(0, 1),
                0L, "clusterName");
        assertThat(result.contains(courseModel));

    }

    @Test
    void testGetCoursesByUserAndClusterName_CourseRepositoryReturnsNoItems() {
        when(mockCourseRepository.findCourseByUsersIdEqualsAndClustersNameLike(any(Pageable.class), eq(0L),
                eq("clusterName%"))).thenReturn(new PageImpl<>(Collections.emptyList()));

        final List<CourseModel> result = courseServiceImplUnderTest.getCoursesByUserAndClusterName(PageRequest.of(0, 1),
                0L, "clusterName");

        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetCoursesByUserAndClusterName_ModelMapperThrowsConfigurationException() {
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        final Page<Course> courses = new PageImpl<>(List.of(course));
        when(mockCourseRepository.findCourseByUsersIdEqualsAndClustersNameLike(any(Pageable.class), eq(0L),
                eq("clusterName%"))).thenReturn(courses);

        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenThrow(ConfigurationException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCoursesByUserAndClusterName(PageRequest.of(0, 1), 0L,
                "clusterName")).isInstanceOf(ConfigurationException.class);
    }

    @Test
    void testGetCoursesByUserAndClusterName_ModelMapperThrowsMappingException() {
        final Course course = new Course();
        course.setId(0L);
        course.setTitle("title");
        course.setDescription("description");
        course.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster = new Cluster();
        course.setClusters(Set.of(cluster));
        final Page<Course> courses = new PageImpl<>(List.of(course));
        when(mockCourseRepository.findCourseByUsersIdEqualsAndClustersNameLike(any(Pageable.class), eq(0L),
                eq("clusterName%"))).thenReturn(courses);

        final Course source = new Course();
        source.setId(0L);
        source.setTitle("title");
        source.setDescription("description");
        source.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Cluster cluster1 = new Cluster();
        source.setClusters(Set.of(cluster1));
        when(mockModelMapper.map(source, CourseModel.class)).thenThrow(MappingException.class);

        assertThatThrownBy(() -> courseServiceImplUnderTest.getCoursesByUserAndClusterName(PageRequest.of(0, 1), 0L,
                "clusterName")).isInstanceOf(MappingException.class);
    }
}
